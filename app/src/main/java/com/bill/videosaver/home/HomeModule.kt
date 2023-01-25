package com.bill.videosaver.home

import com.bill.videosaver.extensions.coroutines.CoroutineDispatchersProvider
import com.bill.videosaver.home.data.DownloadableResourcesRepositoryImpl
import com.bill.videosaver.home.data.file.FileRepository
import com.bill.videosaver.home.data.file.FileRepositoryImpl
import com.bill.videosaver.home.data.file.RemoteFileSaver
import com.bill.videosaver.home.domain.DownloadableResourcesRepository
import com.bill.videosaver.home.domain.ResourceLoadUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File

object HomeModule {
    fun create() = module {
        viewModelOf(::HomeViewModel)

        factoryOf(::DownloadableResourcesRepositoryImpl) bind DownloadableResourcesRepository::class
        factoryOf(::FileRepositoryImpl) bind FileRepository::class
        factory { ResourceLoadUseCase(get(), get()) }
        factory { RemoteFileSaver(CoroutineDispatchersProvider()) }
    }
}