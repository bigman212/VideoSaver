package com.bill.videosaver.home

import androidx.lifecycle.MutableLiveData
import com.bill.videosaver.base.BaseViewModel
import com.bill.videosaver.base.delegate
import com.bill.videosaver.home.domain.FileSaveState
import com.bill.videosaver.home.domain.ResourceLoadUseCase
import timber.log.Timber
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore

class HomeViewModel(
    private val useCase: ResourceLoadUseCase
) : BaseViewModel() {

    data class ViewState(
        val isLoading: Boolean,
        val files: List<FileSaveState>
    )

    val viewState = MutableLiveData(ViewState(isLoading = false, files = emptyList()))

    private var _viewState by viewState.delegate()

    fun <T> List<T>.replace(newValue: T, block: (T) -> Boolean): List<T> {
        return map {
            if (block(it)) newValue else it
        }
    }

    fun <T> Flow<T>.chunked(batchSize: Int): Flow<List<T>> = flow {
        val accumulator = ArrayList<T>()
        var counter = 0

        this@chunked.collect {
            accumulator.add(it)

            if (++counter == batchSize) {
                emit(accumulator)

                accumulator.clear()
                counter = 0
            }
        }

        //emit the remainder if there's any
        if (accumulator.size != 0) {
            emit(accumulator)
        }
    }

    fun loadDownloadableUrls(urls: List<String>) {
        launch {
            val result = useCase.loadResources(urls)

            result.fold(
                ifLeft = { Timber.e(it) },
                ifRight = { flows: List<Flow<FileSaveState>> ->
                    flows.chunked(2)
                        .map { it.merge() }
                        .merge()
                        .runningFold(
                            initial = mutableMapOf<String, FileSaveState>(),
                            operation = { fileToSaveState, newSaveState ->
                                fileToSaveState[newSaveState.fileName] = newSaveState
                                fileToSaveState
                            }
                        )
                        .map { it.toMap() }
                        .onEach {
                            _viewState = _viewState.copy(
                                files = it.values.toList()
                            )
                        }
                        .launchIn(this)
                }
            )
        }
    }
}