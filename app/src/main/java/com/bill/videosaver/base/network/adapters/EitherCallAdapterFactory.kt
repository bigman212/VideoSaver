package com.bill.videosaver.base.network.adapters

import arrow.core.Either
import com.bill.videosaver.data.network.model.BackendRequestError
import com.bill.videosaver.base.network.adapters.either_call.RetrofitEitherCallWrapper
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class EitherCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        require(returnType is ParameterizedType) { "Return type must be a parameterized type." }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != Either::class.java) return null
        check(responseType is ParameterizedType) { "Response type must be a parameterized type." }

        val leftType = getParameterUpperBound(0, responseType)
        if (getRawType(leftType) != BackendRequestError::class.java) return null

        val rightType = getParameterUpperBound(1, responseType)

        val eitherCallWrapper = RetrofitEitherCallWrapper<Any>(rightType)
        return EitherCallAdapter(rightType, eitherCallWrapper)
    }
}