package com.bill.videosaver.home

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object HomeModule {
    fun create() = module {
        viewModelOf(::HomeViewModel)
    }
}