package com.example.recipe

import android.content.Context
import android.net.Uri
import android.webkit.JavascriptInterface
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class WebAppInterface(private val mContext: Context) {

//    @JavascriptInterface
//    fun handler(event: String) {
//        data class ReceiveEvent(
//            val type: String,
//            val data: Any
//        )
//        val event2: ReceiveEvent = Gson().fromJson(event, ReceiveEvent::class.java)
//        println(event2.type)
//        println(event2.data)
//    }

    // called when receiving a customResponse, return an HTML string of the message custom layout
    @JavascriptInterface
    fun customResponse(event: String): String {
        data class UserDefinedData(
            val user_defined_type: String,
        )
        data class CustomResponseMessage(
            val values: Any,
            val user_defined: UserDefinedData,
        )
        data class CustomResponseData(
            val message: CustomResponseMessage,
            val fullMessage: Any,
        )
        val event2: CustomResponseData = Gson().fromJson(event, CustomResponseData::class.java)
        if(event2.message.user_defined.user_defined_type == "image_upload") {
            return """<button id="image-upload-button" type="button" style="margin: 5px 0px 15px;">Upload Image</button>"""
        }
        return """<div>Error: unknown custom action.</button>"""
    }

    // opens the gallery for the user to select an image
    @JavascriptInterface
    fun openGallery() {
        println("YEAHYA")
        (mContext as MainActivity).imageLauncher.launch("image/*")
    }
}