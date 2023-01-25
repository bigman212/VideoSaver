package com.bill.videosaver.data.network.api.downloadable_urls

import arrow.core.Either
import com.bill.videosaver.home.data.GetDownloadableResourceError.InvalidUrlsFound
import java.net.URL
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonArray

@kotlinx.serialization.Serializable
class DownloadableUrlsRequest private constructor(
    @SerialName("urls")
    val urls: List<String>,
    @SerialName("errors")
    val errors: JsonArray = JsonArray(emptyList())
) {
    companion object {
        fun create(urls: List<String>): Either<InvalidUrlsFound, DownloadableUrlsRequest> {
            return getValidRequestInstance(urls)
        }

        private fun getValidRequestInstance(
            allUrls: List<String>
        ): Either<InvalidUrlsFound, DownloadableUrlsRequest> {
            val validUrls = allUrls.filter { isValidUrl(it) }
            val invalidUrls = validUrls - allUrls.toSet()
            return Either.conditionally(
                test = invalidUrls.isEmpty(),
                ifFalse = { InvalidUrlsFound(invalidUrls) },
                ifTrue = { DownloadableUrlsRequest(validUrls) }
            )
        }

        private fun isValidUrl(url: String): Boolean = kotlin.runCatching { URL(url).toURI() }.isSuccess
    }
}