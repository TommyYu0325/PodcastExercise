package com.example.podcastexercise.repository

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class BaseRepository {

    protected fun<T> create(apiCall: Single<T>): Single<T> {
        return apiCall
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { obj ->
                if (obj == null) {
                    throw IllegalStateException("null data")
                }
            }
    }

    protected fun create(apiCall: Completable): Completable {
        return apiCall
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
