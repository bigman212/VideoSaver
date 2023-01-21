package com.bill.videosaver.extensions

import okhttp3.Request
import okhttp3.Response
import okio.Buffer

fun Request.bodyAsString(): String = kotlin.runCatching {
    val requestCopy: Request = this.newBuilder().build()
    val buffer = Buffer()
    requestCopy.body?.writeTo(buffer)
    buffer.readUtf8()
}
    .getOrDefault("")

fun Response.bodyAsString(): String = peekBody(Long.MAX_VALUE).string()
