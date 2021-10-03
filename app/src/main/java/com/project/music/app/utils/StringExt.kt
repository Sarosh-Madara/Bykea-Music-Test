package com.project.music.app.utils

import android.os.Build
import android.text.Html
import android.util.Log
import android.widget.TextView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat


fun String.getIntValue(): Int {
    try {
        try {
            return this.toInt()
        } catch (nfe: NumberFormatException) {
            return 0
        }
    } catch (e: Exception) {
        return 0
    }
}

fun String.getDoubletValue(): Double {
    try {
        try {
            return this.toDouble()
        } catch (nfe: NumberFormatException) {
            return 0.00
        }
    } catch (e: Exception) {
        return 0.00
    }
}

fun String.getFlotValue(): Float {
    try {
        try {
            return this.toFloat()
        } catch (nfe: NumberFormatException) {
            return 0f
        }
    } catch (e: Exception) {
        return 0f
    }
}

fun String.htmlTextRender(textView: TextView) {
    if (!this.isNullOrEmpty()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(this));
        }
    }

}

fun Any?.LogData(TAG: String) {
    Log.e(TAG, "${this}")
}

fun String.createPartFromString(): RequestBody? {
    try {
        return this.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    } catch (e: Exception) {
        return null
    }
}

fun String.changeDateFormat(INPUT_FORMAT: String, OUTPUT_FORMAT: String): String? {
    try {
        val parser = SimpleDateFormat(INPUT_FORMAT)
        val formatter = SimpleDateFormat(OUTPUT_FORMAT)
        return formatter.format(parser.parse(this))
    } catch (e: Exception) {
        return this
    }
}

//fun String.createPartFromString(): RequestBody? {
//    try {
//        return this.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
//    } catch (e: Exception) {
//        return null
//    }
//}


fun File.createRequestForImage(parameterName: String): MultipartBody.Part {
    val requestImageFile = this.asRequestBody("image/*".toMediaTypeOrNull())
    val data = MultipartBody.Part.createFormData(parameterName, this.getName(), requestImageFile)
    return data
}