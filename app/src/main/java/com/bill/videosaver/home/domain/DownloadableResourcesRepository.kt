package com.bill.videosaver.home.domain

import androidx.annotation.IntRange
import android.net.Uri
import arrow.core.Either
import com.bill.videosaver.data.network.api.downloadable_urls.DownloadableResource
import com.bill.videosaver.home.data.GetDownloadableResourceError
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

sealed class FileSaveState {
    abstract val fileName: String

    data class SavingInProgress(
        override val fileName: String,
        @IntRange(0, 100) val progress: Int
    ) : FileSaveState()

    data class SavingSuccess(
        override val fileName: String,
        val fileUri: Uri
    ) : FileSaveState()

    data class SavingFailed(
        override val fileName: String,
        val error: Throwable
    ) : FileSaveState()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FileSaveState) return false

        if (fileName != other.fileName) return false

        return true
    }

    override fun hashCode(): Int {
        return fileName.hashCode()
    }
}

interface DownloadFileApi {
    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String): ResponseBody
}

interface DownloadableResourcesRepository {
    suspend fun getDownloadableResources(
        resourcesUrls: List<String>
    ): Either<GetDownloadableResourceError, List<DownloadableResource>>
}

