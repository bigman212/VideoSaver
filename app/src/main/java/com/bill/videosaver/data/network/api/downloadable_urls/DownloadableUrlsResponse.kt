package com.bill.videosaver.data.network.api.downloadable_urls

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class DownloadableUrlsResponse(
    @SerialName("urls")
    val urls: List<String>
){
    fun toDomain(): List<DownloadableResource> {
        return urls.mapIndexed { index, value ->
            DownloadableResource(
                resourceName = "${value.take(10)}_$index",
                validUrl = value
            )
        }
    }
}

