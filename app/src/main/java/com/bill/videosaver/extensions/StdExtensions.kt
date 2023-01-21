package com.bill.videosaver.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.ext.getFullName
import timber.log.Timber

fun emptyString() = ""

fun StringBuilder.appendBreakLine() {
    append("\n")
}

fun <R> unsafeLazy(initializer: () -> R): Lazy<R> = lazy(LazyThreadSafetyMode.NONE, initializer)

inline fun <reified Type> Gson.fromJsonReified(json: String): Type? {
    val result = fromJson<Type>(json, object : TypeToken<Type>() {}.type)
    if (result == null) {
        Timber.e(IllegalArgumentException("Couldn't parse ${Type::class.getFullName()} from json: ${json.take(30)}"))
    }
    return result
}

fun Result<*>.invokeAndForget() {
    getOrNull()
}