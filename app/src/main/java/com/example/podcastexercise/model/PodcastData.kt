package com.example.podcastexercise.model

data class PodCastData (
    val podcast: List<PodCast> ? = null
)

data class PodCast(
    val artistName: String? = null,
    val artworkUrl100: String? = null,
    val id: Int,
    val name: String? = null
)