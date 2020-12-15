package com.example.podcastexercise.repository

import com.example.podcastexercise.model.BaseData
import com.example.podcastexercise.model.PodCastData
import com.example.podcastexercise.model.PodCastDetailData
import com.example.podcastexercise.remote.PodcastService
import io.reactivex.Single

class PodcastRepository(private val service: PodcastService) : BaseRepository() {

    fun getCasts(): Single<BaseData<PodCastData>> {
        return create(service.getCasts())
    }

    fun getCastDetail(): Single<BaseData<PodCastDetailData>> {
        return create(service.getCastDetail())
    }

}