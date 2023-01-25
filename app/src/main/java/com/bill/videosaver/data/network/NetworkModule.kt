package com.bill.videosaver.data.network

import com.bill.videosaver.BuildConfig
import com.bill.videosaver.base.network.adapters.EitherCallAdapterFactory
import com.bill.videosaver.data.network.api.downloadable_urls.DownloadableResourcesApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object NetworkModule {
    fun create() = module {
        single { Json{ ignoreUnknownKeys = true } }
        single {
            Retrofit.Builder()
                .baseUrl("https://google.com")
                .addConverterFactory(createJsonSerializer())
                .addCallAdapterFactory(EitherCallAdapterFactory())
                .build()
        }
        single { retrofit().create<DownloadableResourcesApi>() }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun createJsonSerializer(): Converter.Factory = Json.asConverterFactory("application/json".toMediaType())
}

fun Scope.retrofit() = get<Retrofit>()