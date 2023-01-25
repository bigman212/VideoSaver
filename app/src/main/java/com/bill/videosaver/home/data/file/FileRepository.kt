package com.bill.videosaver.home.data.file

import com.bill.videosaver.data.network.api.downloadable_urls.DownloadableResource
import com.bill.videosaver.home.domain.FileSaveState
import kotlinx.coroutines.flow.Flow

interface FileRepository {
    fun downloadAndSaveFile(
        resource: DownloadableResource
    ): Flow<FileSaveState>
}
