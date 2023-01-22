package com.bill.videosaver.home

import androidx.lifecycle.MutableLiveData
import com.bill.videosaver.base.BaseViewModel
import com.bill.videosaver.base.delegate

class HomeViewModel : BaseViewModel() {

    data class ViewState(
        val isLoading: Boolean
    )

    val viewState = MutableLiveData(ViewState(isLoading = false))

    private var _viewState by viewState.delegate()
}