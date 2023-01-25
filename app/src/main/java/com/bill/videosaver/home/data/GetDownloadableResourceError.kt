package com.bill.videosaver.home.data

sealed class GetDownloadableResourceError(override val cause: Throwable? = null) : Throwable() {
    class InvalidUrlsFound(val invalidUrls: List<String>) : GetDownloadableResourceError()
    class NotSupportedUrls(val notSupportedUrls: List<String>): GetDownloadableResourceError()
    class UnknownError(cause: Throwable): GetDownloadableResourceError(cause)
}