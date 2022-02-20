package com.rivas.testandroiddeveloper.utils.extensions

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rivas.testandroiddeveloper.AndroidApp
import com.rivas.testandroiddeveloper.R
import java.text.SimpleDateFormat

internal fun String.toJson(): JsonObject = JsonParser
    .parseString(this)
    .asJsonObject

internal fun String.isJson() = try {
    val gson = Gson()
    gson.fromJson(this, Any::class.java)
    true
} catch (ex: com.google.gson.JsonSyntaxException) {
    false
}

internal fun String.toApiException() =
    Exception(this)

@SuppressLint("SimpleDateFormat")
internal fun String.toDate(): String {
    if(this.isEmpty())
        return AndroidApp.appContext.getString(R.string.unknown)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val dateFormatToString = SimpleDateFormat("dd MMM yyyy")
    return dateFormatToString.format(dateFormat.parse(this)!!)
}

