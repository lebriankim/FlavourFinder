package com.example.recipe

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        // add the interface which forms the bridge with the javascript functions
        webView.addJavascriptInterface(WebAppInterface(this), "Android")
        // load the main page containing IBM chat view
        webView.loadUrl("file:///android_asset/index.html")
    }

    // callback for the image uploader, sends the result to Watson
    val imageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if(uri != null && DocumentFile.fromSingleUri(this, uri)?.exists() == true) {
            webView.loadUrl("javascript:imageUploaded(true)")
            lifecycleScope.launch {
                val namesList = withContext(Dispatchers.IO) {
                    sendImageToYolo(uri)
                }
                withContext(Dispatchers.Main) {
                    webView.loadUrl("javascript:sendFoodItems(\"$namesList\")")
                }
            }
        } else {
            webView.loadUrl("javascript:imageUploaded(false)")
        }
    }

    private suspend fun sendImageToYolo(uri: Uri): String {
        val stream = contentResolver.openInputStream(uri)
        var bitmap = BitmapFactory.decodeStream(stream)
        val maxSize = 1280
        val bitmapRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
        bitmap = if (bitmapRatio > 1 && bitmap.width > maxSize) {
            Bitmap.createScaledBitmap(bitmap, maxSize, (maxSize / bitmapRatio).toInt(), true)
        } else if(bitmapRatio < 1 && bitmap.height > maxSize) {
            Bitmap.createScaledBitmap(bitmap, (maxSize * bitmapRatio).toInt(), 640, true)
        } else {
            bitmap
        }
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
        val b = outputStream.toByteArray()

        val bodyBuilder = MultipartEntityBuilder.create()
        bodyBuilder.addBinaryBody("image", b, ContentType.IMAGE_JPEG, uri.lastPathSegment)
        bodyBuilder.addTextBody("size", "640")
        bodyBuilder.addTextBody("confidence", "0.25")
        bodyBuilder.addTextBody("iou", "0.45")
        val entity = bodyBuilder.build()

        val url = URL("https://api.ultralytics.com/v1/predict/af6qKxjR7JKNq39pWmPK")
        val connection = withContext(Dispatchers.IO) { url.openConnection() as HttpURLConnection }
        connection.requestMethod = "POST"
        connection.setRequestProperty("x-api-key", "df1264c80c912652a1c869130e61d6e9ddfe1de730")
        connection.setRequestProperty(entity.contentType.name, entity.contentType.value)
        connection.setRequestProperty("Accept", "application/json")
        connection.doInput = true
        connection.doOutput = true

        entity.writeTo(connection.outputStream)

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(response)
            val dataArray = jsonObject.getJSONArray("data")
            val namesList = mutableSetOf<String>()
            for (i in 0 until dataArray.length()) {
                val dataObject = dataArray.getJSONObject(i)
                val name = dataObject.getString("name")
                namesList.add(name)
            }
            val jsonArray = JSONArray(namesList).toString().replace("\"", "'")
            println(jsonArray)
            return jsonArray
        } else {
            println(connection.responseCode)
            println(connection.responseMessage)
            BufferedReader(connection.errorStream.reader()).use {
                println(it.readText())
            }
            return ""
        }
    }
}