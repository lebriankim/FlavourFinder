package com.example.recipe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import java.nio.file.Paths

class MainActivity : AppCompatActivity() {
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        val exists = uri != null && DocumentFile.fromSingleUri(this, uri)?.exists() == true
        webView.loadUrl("javascript:imageUploaded($exists)")
    }
}