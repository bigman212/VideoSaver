package com.bill.videosaver.base.network.adapters.either_call

import arrow.core.left
import arrow.core.right
import com.bill.videosaver.data.network.model.BackendRequestError
import com.bill.videosaver.base.network.adapters.BackendEither
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

class RetrofitEitherCallWrapper<R>(private val successType: Type) {

    fun wrapOnSuccess(rawResponse: Response<R>): Response<BackendEither<R>> {
        val successBody = rawResponse.body()
        val errorBody = rawResponse.errorBody()?.string().orEmpty()
        return when {
            !rawResponse.isSuccessful -> BackendRequestError.BadRequest(errorBody).left()
            successBody == null -> BackendRequestError.EmptyBody().left()
            successType == Unit::class.java -> Unit.right() as BackendEither<R>
            else -> successBody.right()
        }
            .wrapInResponse()
    }


    fun wrapOnFailure(throwable: Throwable): Response<BackendEither<R>> {
        return when (throwable) {
            is IOException -> BackendRequestError.IoError(throwable)
            else -> BackendRequestError.UnknownError(throwable)
        }
            .left()
            .wrapInResponse()
    }

    private fun BackendEither<R>.wrapInResponse(): Response<BackendEither<R>> {
        return Response.success(this)
    }
}