package com.example.recipe

import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.webkit.WebView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.MultipartEntityBuilder
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        // add the interface which forms the bridge with the javascript functions
        webView.addJavascriptInterface(WebAppInterface(this), "Android")
        // load the main page containing IBM chat view
        webView.loadUrl("file:///android_asset/index.html")
    }

    // callback for the image uploader, sends the result to Watson
    val imageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()) { uri: Uri? ->
        println(uri)
        if(uri != null && DocumentFile.fromSingleUri(this, uri)?.exists() == true) {
            sendImageToYolo(uri)
            webView.loadUrl("javascript:imageUploaded(true)")
        }
        webView.loadUrl("javascript:imageUploaded(false)")
    }

    private fun sendImageToYolo(uri: Uri) {
        val byteArray = FileInputStream(contentResolver.openFileDescriptor(uri, "r")?.fileDescriptor).use { it.readBytes() }
        val bodyBuilder = MultipartEntityBuilder.create()
        bodyBuilder.addBinaryBody("image", byteArray, ContentType.IMAGE_JPEG, "test.jpg")
        bodyBuilder.addTextBody("size", "640")
        bodyBuilder.addTextBody("confidence", "0.25")
        bodyBuilder.addTextBody("iou", "0.45")
        val entity = bodyBuilder.build()

        val url = URL("https://api.ultralytics.com/v1/predict/HUSgcANncfJqQM5D64t6")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("x-api-key", "a85ba500ef371352ebbd1973f96f26f4d0065d030e")
        connection.setRequestProperty(entity.contentType.name, entity.contentType.value)
        connection.setRequestProperty("Accept", "application/json");
        connection.doOutput = true
        connection.connect()

        try {
            entity.writeTo(connection.outputStream)

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                println(response)
            } else {
                println(connection.responseCode)
                println(connection.responseMessage)
                BufferedReader(connection.errorStream.reader()).use {
                    println(it.readText())
                }
            }
        } finally {
            connection.disconnect()
        }
    }
}