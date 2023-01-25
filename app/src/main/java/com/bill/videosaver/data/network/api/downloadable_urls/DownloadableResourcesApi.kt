package com.bill.videosaver.data.network.api.downloadable_urls

import com.bill.videosaver.base.network.adapters.BackendEither
import retrofit2.http.Body
import retrofit2.http.POST

interface DownloadableResourcesApi {
    @POST("/")
    suspend fun fetchDownloadableUrls(@Body body: DownloadableUrlsRequest)
        : BackendEither<DownloadableUrlsResponse>
}