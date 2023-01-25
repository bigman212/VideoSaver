package com.bill.videosaver.home.domain

import arrow.core.Either
import com.bill.videosaver.home.data.GetDownloadableResourceError
import com.bill.videosaver.home.data.file.FileRepository
import kotlinx.coroutines.flow.Flow

class ResourceLoadUseCase(
    private val downloadableResourcesRepository: DownloadableResourcesRepository,
    private val fileRepository: FileRepository
) {

    suspend fun loadResources(urls: List<String>): Either<GetDownloadableResourceError, List<Flow<FileSaveState>>> {
        return downloadableResourcesRepository.getDownloadableResources(urls)
            .map { it.map(fileRepository::downloadAndSaveFile) }
    }
}