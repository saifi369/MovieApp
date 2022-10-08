package com.starzplay.movieapp.domain.model


abstract class MediaItem {
    abstract var id: Int?
    abstract var mediaType: String?
}

data class VideoItem(
    override var id: Int? = null,
    override var mediaType: String?,
    var title: String? = null,
    val overview: String? = null,
    val backdropPath: String? = null,
    val posterPath: String? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
) : MediaItem()

data class PersonItem(
    override var id: Int? = null,
    override var mediaType: String?,
    var name: String? = null,
    val gender: Int? = null,
    val knownFor: List<MediaItem?>? = null,
    val knownForDepartment: String? = null,
    val profilePath: String? = null
) : MediaItem()
