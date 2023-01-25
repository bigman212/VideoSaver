package com.bill.videosaver.home.data.file

import androidx.core.net.toUri
import android.net.Uri
import com.bill.videosaver.extensions.coroutines.CoroutineDispatchersProvider
import com.bill.videosaver.home.domain.FileSaveState
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteFileSaver(private val dispatchers: CoroutineDispatchersProvider) {

    fun saveFile(
        fileName: String,
        responseProvider: suspend () -> ResponseBody,
    ): Flow<FileSaveState> {
        return flow {
            emit(FileSaveState.SavingInProgress(progress = 0, fileName = fileName))
//            val response = responseProvider.invoke()
//            val inputStream = response.byteStream()
//            val totalBytes = 12340L
//            val destinationFile = File(fileName)
//            val outputStream = destinationFile.outputStream()
            try {
                emit(FileSaveState.SavingInProgress(progress = 25, fileName = fileName))
                kotlinx.coroutines.delay(1000)
                emit(FileSaveState.SavingInProgress(progress = 50, fileName = fileName))
                kotlinx.coroutines.delay(1000)
                emit(FileSaveState.SavingInProgress(progress = 100, fileName = fileName))
                kotlinx.coroutines.delay(1000)
//                saveFile(totalBytes, inputStream, outputStream)
                emit(FileSaveState.SavingSuccess(fileName = fileName, Uri.EMPTY))
            } catch (e: Throwable) {
                Timber.e(e)
                emit(FileSaveState.SavingFailed(fileName = fileName, e))
            } finally {
//                inputStream.close()
//                outputStream.close()
            }
        }
            .flowOn(dispatchers.io())
            .distinctUntilChanged()
    }

    private suspend fun FlowCollector<FileSaveState>.saveFile(
        totalBytes: Long,
        inputStream: InputStream,
        outputStream: OutputStream
    ) {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var progressBytes = 0L
        var bytes = inputStream.read(buffer)
        while (bytes >= 0) {
            outputStream.write(buffer, 0, bytes)
            progressBytes += bytes
            bytes = inputStream.read(buffer)
            val progress = progressBytes * 100 / totalBytes
//            emit(FileSaveState.SavingInProgress(progress = progress.toInt()))
//            kotlinx.coroutines.delay(1000)
        }
    }
}