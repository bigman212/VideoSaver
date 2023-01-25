package com.bill.videosaver.home.data

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.right
import com.bill.videosaver.data.network.api.downloadable_urls.DownloadableResource
import com.bill.videosaver.data.network.api.downloadable_urls.DownloadableResourcesApi
import com.bill.videosaver.data.network.api.downloadable_urls.DownloadableUrlsRequest
import com.bill.videosaver.data.network.model.BackendRequestError
import com.bill.videosaver.home.domain.DownloadableResourcesRepository
import kotlinx.serialization.json.Json

class DownloadableResourcesRepositoryImpl(
    private val api: DownloadableResourcesApi,
    private val jsonSerializer: Json
) : DownloadableResourcesRepository {
    private fun s(size: Int): List<DownloadableResource> {
        return (0..size).map {
            DownloadableResource("name_$it", "name_1_url")
        }
    }

    override suspend fun getDownloadableResources(
        resourcesUrls: List<String>,
    ): Either<GetDownloadableResourceError, List<DownloadableResource>> {
        return either {
            val request = DownloadableUrlsRequest.create(resourcesUrls).bind()
            val response = api.fetchDownloadableUrls(request).mapLeft(::mapErrorsToDomain).bind()
            response.toDomain()
        }
    }

    class MyException1(override val cause: Throwable) : Throwable()
    class MyException2 : Throwable()

    private suspend fun example(): Either<MyException1, String> {
        return "".right()
    }

    private suspend fun example2(s: String): Either<MyException2, String> {
        return "".right()
    }

    private fun mapErrorsToDomain(error: BackendRequestError): GetDownloadableResourceError {
        return if (error is BackendRequestError.BadRequest) {
            GetDownloadableResourceError.InvalidUrlsFound(emptyList())
        } else {
            GetDownloadableResourceError.UnknownError(error)
        }
    }
}