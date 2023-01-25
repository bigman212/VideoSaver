package com.bill.videosaver.home.data.file

import com.bill.videosaver.data.network.api.downloadable_urls.DownloadableResource
import com.bill.videosaver.home.domain.DownloadFileApi
import com.bill.videosaver.home.domain.FileSaveState
import okhttp3.OkHttp
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.http.RealResponseBody
import kotlinx.coroutines.flow.Flow

class FileRepositoryImpl(
    private val fileSaver: RemoteFileSaver,
//    private val api: DownloadFileApi
) : FileRepository {
    override fun downloadAndSaveFile(resource: DownloadableResource): Flow<FileSaveState> {
        return fileSaver.saveFile(
            fileName = resource.resourceName,
            responseProvider = { "".toResponseBody() }
        )
    }
}