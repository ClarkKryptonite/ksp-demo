package com.example.launchbase.util

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * @author kun
 * @since 2023.03.01
 */
private val gson = GsonBuilder()
    .create()
private val escapeRegex = "\"".toRegex()

fun <T> T.toJsonStr(): String {
    return gson.toJson(this)
}

fun String.toEscapeStr(): String = replace(escapeRegex) {
    "\\${it.value}"
}


fun <T : Any> String.parseFromJson(clazz: Class<T>): T {
    return gson.fromJson(this, clazz)
}

fun <T : Any> String.parseFromJson(): T {
    val token = object : TypeToken<T>() {}
    return gson.fromJson(this, token.type)
}