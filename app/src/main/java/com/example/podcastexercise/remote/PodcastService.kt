package com.example.podcastexercise.remote

import com.example.podcastexercise.BuildConfig
import com.example.podcastexercise.model.BaseData
import com.example.podcastexercise.model.PodCastData
import com.example.podcastexercise.model.PodCastDetailData
import io.reactivex.Single
import retrofit2.http.GET

interface PodcastService {

    @GET(BuildConfig.SERVER_BASE_URL + "getcasts")
    fun getCasts(): Single<BaseData<PodCastData>>


    @GET(BuildConfig.SERVER_BASE_URL + "getcastdetail")
    fun getCastDetail(): Single<BaseData<PodCastDetailData>>

}