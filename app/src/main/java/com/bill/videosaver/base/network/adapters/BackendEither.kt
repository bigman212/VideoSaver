package com.bill.videosaver.base.network.adapters

import arrow.core.Either
import com.bill.videosaver.data.network.model.BackendRequestError

typealias BackendEither<R> = Either<BackendRequestError, R>