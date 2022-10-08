package com.starzplay.movieapp.domain

import com.starzplay.data.remote.dto.MediaTypeMovie
import com.starzplay.data.remote.dto.MediaTypePerson
import com.starzplay.data.remote.dto.MediaTypeTv
import com.starzplay.data.remote.dto.TMDBSearchDto
import com.starzplay.movieapp.domain.model.MediaItem
import com.starzplay.movieapp.domain.model.PersonItem
import com.starzplay.movieapp.domain.model.VideoItem

fun MediaTypeMovie.toMovieItem() = VideoItem(
    id = this.id,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath,
    backdropPath = backdropPath,
    mediaType = contentMediaType,
    overview = overview
)

fun MediaTypeTv.toMovieItem() = VideoItem(
    id = this.id,
    title = name,
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath,
    backdropPath = backdropPath,
    mediaType = contentMediaType,
    overview = overview
)

fun MediaTypePerson.toPersonItem() = PersonItem(
    id = id,
    name = name,
    gender = gender,
    knownFor = knownFor,
    knownForDepartment = knownForDepartment,
    profilePath = profilePath,
    mediaType = contentMediaType
)


fun TMDBSearchDto?.asDomainModel(): List<MediaItem?> =
    this?.results?.map {
        when (it) {
            is MediaTypeMovie -> it.toMovieItem()
            is MediaTypeTv -> it.toMovieItem()
            is MediaTypePerson -> it.toPersonItem()
            else -> {
                null
            }
        }
    } ?: emptyList()