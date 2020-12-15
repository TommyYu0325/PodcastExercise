package com.example.podcastexercise.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PodCastDetailData (
    val collection: CastCollection? = null
)

data class CastCollection(
    val artistId: Int,
    val artistName: String? = null,
    val artworkUrl100: String? = null,
    val artworkUrl600: String? = null,
    val collectionId: Int,
    val collectionName: String? = null,
    val contentFeed: List<ContentFeed>? = null,
    val country: String? = null,
    val genreIds: String? = null,
    val genres: String? = null,
    val releaseDate: String?= null
)

@Parcelize
data class ContentFeed(
    val contentUrl: String? = null,
    val desc: String? = null,
    val publishedDate: String? = null,
    val title: String? = null
) : Parcelable