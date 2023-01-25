package com.bill.videosaver.base.network.adapters.either_call

import com.bill.videosaver.base.network.adapters.BackendEither
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class EitherRetrofitCall<R>(
    private val rootCall: Call<R>,
    private val successType: Type,
    private val eitherWrapper: RetrofitEitherCallWrapper<R>
) : Call<BackendEither<R>> {
    override fun execute(): Response<BackendEither<R>> =
        runCatching(rootCall::execute)
            .map(eitherWrapper::wrapOnSuccess)
            .getOrElse(eitherWrapper::wrapOnFailure)

    override fun enqueue(callback: Callback<BackendEither<R>>) {
        rootCall.enqueue(rawResponseHandler(eitherCallback = callback))
    }

    private fun rawResponseHandler(eitherCallback: Callback<BackendEither<R>>): Callback<R> {
        val eitherCall = this
        return object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                eitherCallback.onResponse(eitherCall, eitherWrapper.wrapOnSuccess(response))
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                eitherCallback.onResponse(eitherCall, eitherWrapper.wrapOnFailure(t))
            }
        }
    }

    override fun clone(): Call<BackendEither<R>> = EitherRetrofitCall(rootCall, successType, eitherWrapper)

    override fun request(): Request = rootCall.request()

    override fun isExecuted(): Boolean = rootCall.isExecuted

    override fun cancel() {
        rootCall.cancel()
    }

    override fun isCanceled(): Boolean = rootCall.isCanceled

    override fun timeout(): Timeout = rootCall.timeout()
}