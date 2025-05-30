package com.cuan.catatankeuangan.repository

import android.content.Context
import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

class CloudinaryService(context: Context) {

    init {
        val config: HashMap<String, String> = HashMap()
        config["cloud_name"] = "dntcfibpc"
        config["api_key"] = "199625824716529"
        config["api_secret"] = "VKpmeYnOes14CN-V5M1JIwnAv2c"
        MediaManager.init(context, config)
    }

    fun uploadImage(
        uri: Uri,
        publicId: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        MediaManager.get()
            .upload(uri)
            .option("public_id", publicId)
            .callback(object : UploadCallback {
                override fun onStart(requestId: String) {}
                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {}
                override fun onSuccess(requestId: String, resultData: MutableMap<Any?, Any?>?) {
                    val url = resultData?.get("secure_url").toString()
                    onSuccess(url)
                }

                override fun onError(requestId: String, error: ErrorInfo?) {
                    onError(error?.description ?: "Upload error")
                }

                override fun onReschedule(requestId: String, error: ErrorInfo?) {}
            })
            .dispatch()
    }
}
