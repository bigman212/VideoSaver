package com.bill.videosaver.base.network.adapters

import com.bill.videosaver.base.network.adapters.either_call.EitherRetrofitCall
import com.bill.videosaver.base.network.adapters.either_call.RetrofitEitherCallWrapper
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class EitherCallAdapter<R>(
    private val successType: Type,
    private val eitherCallWrapper: RetrofitEitherCallWrapper<R>
) : CallAdapter<R, Call<BackendEither<R>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<R>): Call<BackendEither<R>> = EitherRetrofitCall(call, successType, eitherCallWrapper)
}