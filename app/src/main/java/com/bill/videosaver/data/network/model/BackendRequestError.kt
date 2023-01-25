package com.bill.videosaver.data.network.model

import retrofit2.Response
import java.io.IOException

@kotlinx.serialization.Serializable
class BackendRequestErrorBody(
    val type: String,
    val message: String
)

sealed class BackendRequestError(cause: Throwable? = null) : Throwable(cause) {
    class IoError(override val cause: IOException) : BackendRequestError(cause)

    class BadRequest(
        val errorBody: String
    ): BackendRequestError()

    object UnauthorizedRequest: BackendRequestError()

    class EmptyBody : BackendRequestError()
    class UnknownError(override val cause: Throwable) : BackendRequestError(cause)
}


