package com.bill.videosaver.extensions

fun emptyString() = ""

fun StringBuilder.appendBreakLine() {
    append("\n")
}

fun <R> unsafeLazy(initializer: () -> R): Lazy<R> = lazy(LazyThreadSafetyMode.NONE, initializer)


fun Result<*>.invokeAndForget() {
    getOrNull()
}