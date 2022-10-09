package com.starzplay.movieapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class MediaItem : Parcelable {
    open var id: Int? = null
    open var mediaType: String? = null
    open var fullPosterUrl: String? = null
}

@Parcelize
data class VideoItem(
    override var id: Int? = null,
    override var mediaType: String?,
    var title: String? = null,
    val overview: String? = null,
    val backdropPath: String? = null,
    val posterPath: String? = null,
    val voteAverage: Double? = null,
    val originalLanguage: String? = null,
    val releaseDate: String? = null,
    val voteCount: Int? = null,
) : MediaItem(), Parcelable {
    override var fullPosterUrl: String? = "https://image.tmdb.org/t/p/w500${posterPath}"
    val fullBackdropUrl = "https://image.tmdb.org/t/p/w500${backdropPath}"
}

@Parcelize
data class PersonItem(
    override var id: Int? = null,
    override var mediaType: String?,
    var name: String? = null,
    val gender: Int? = null,
    val knownFor: List<MediaItem?>? = null,
    val knownForDepartment: String? = null,
    val profilePath: String? = null,
    val popularity: Double? = null,
) : MediaItem(), Parcelable {
    override var fullPosterUrl: String? = "https://image.tmdb.org/t/p/w500${profilePath}"
}
