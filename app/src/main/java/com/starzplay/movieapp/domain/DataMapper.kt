package com.starzplay.movieapp.domain

import com.starzplay.data.remote.dto.*
import com.starzplay.movieapp.domain.model.*

fun MediaTypeMovie.toMovieItem() = VideoItem(
    id = this.id,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath,
    backdropPath = backdropPath,
    mediaType = contentMediaType,
    overview = overview,
    originalLanguage = originalLanguage,
    releaseDate = releaseDate
)

fun MediaTypeTv.toMovieItem() = VideoItem(
    id = this.id,
    title = name,
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath,
    backdropPath = backdropPath,
    mediaType = contentMediaType,
    overview = overview,
    originalLanguage = originalLanguage,
    releaseDate = firstAirDate
)

fun MediaTypePerson.toPersonItem() = PersonItem(
    id = id,
    name = name,
    gender = gender,
    knownFor = knownFor?.map {
        when (it) {
            is MediaTypeMovie -> it.toMovieItem()
            is MediaTypeTv -> it.toMovieItem()
            else -> {
                null
            }
        }
    },
    knownForDepartment = knownForDepartment,
    profilePath = profilePath,
    mediaType = contentMediaType,
    popularity = popularity
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

fun MovieDetailDto.asDomainModel() = MovieDetail(
    adult = adult,
    backdropPath = backdropPath,
    genres = genres?.map { it?.name } ?: emptyList(),
    id = id,
    imdbId = imdbId,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    runtime = runtime,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun TvDetailDto.asDomainModel() = TvDetail(
    adult = adult,
    backdropPath = backdropPath,
    firstAirDate = firstAirDate,
    genres = genres?.map { it?.name } ?: emptyList(),
    id = id,
    inProduction = inProduction,
    name = name,
    numberOfEpisodes = numberOfEpisodes,
    numberOfSeasons = numberOfSeasons,
    originalLanguage = originalLanguage,
    originalName = originalName,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun PersonDetailDto.asDomainModel() = PersonDetail(
    adult = adult,
    alsoKnownAs,
    biography = biography,
    birthday = birthday,
    gender = gender,
    id = id,
    imdbId = imdbId,
    knownForDepartment = knownForDepartment,
    name = name,
    placeOfBirth = placeOfBirth,
    popularity = popularity,
    profilePath = profilePath
)