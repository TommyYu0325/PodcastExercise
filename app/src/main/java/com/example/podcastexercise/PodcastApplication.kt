package com.example.podcastexercise

import androidx.multidex.MultiDexApplication
import com.example.podcastexercise.di.appModule
import com.example.podcastexercise.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PodcastApplication : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PodcastApplication)
            modules(listOf(remoteModule, appModule))
        }
    }
}