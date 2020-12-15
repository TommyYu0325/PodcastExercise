package com.example.podcastexercise.di

import com.example.podcastexercise.BuildConfig
import com.example.podcastexercise.remote.PodcastService
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers.single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {
    single { createRetrofitClient() }
    factory { createService<PodcastService>(get()) }

}

private inline fun <reified T> createService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

private fun createRetrofitClient(): Retrofit {
    val builder = createRetrofit()
    return builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}


private fun createRetrofit(): Retrofit.Builder {
    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor { chain: Interceptor.Chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .method(original.method(), original.body())
            .build()
        chain.proceed(request)
    }

    //WORKAROUND!! SHOULD BE FXIED!!
    httpClient.connectTimeout(30, TimeUnit.SECONDS)
    httpClient.writeTimeout(30, TimeUnit.SECONDS)
    httpClient.readTimeout(30, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
    }


    return Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(httpClient.build())
}