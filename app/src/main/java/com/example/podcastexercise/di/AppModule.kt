package com.example.podcastexercise.di

import com.example.podcastexercise.main.architecture.PodCastViewModel
import com.example.podcastexercise.repository.PodcastRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { PodCastViewModel(get()) }

    factory { PodcastRepository(get()) }

}