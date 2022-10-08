package com.starzplay.movieapp.domain.model


data class MovieDetail(
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val genres: List<String?>? = null,
    val id: Int? = null,
    val imdbId: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val runtime: Int? = null,
    val title: String? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null
)

data class PersonDetail(
    val adult: Boolean? = null,
    val alsoKnownAs: List<String?>? = null,
    val biography: String? = null,
    val birthday: String? = null,
    val gender: Int? = null,
    val id: Int? = null,
    val imdbId: String? = null,
    val knownForDepartment: String? = null,
    val name: String? = null,
    val placeOfBirth: String? = null,
    val popularity: Double? = null,
    val profilePath: String? = null
)

data class TvDetail(
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val firstAirDate: String? = null,
    val genres: List<String?>? = null,
    val id: Int? = null,
    val inProduction: Boolean? = null,
    val name: String? = null,
    val numberOfEpisodes: Int? = null,
    val numberOfSeasons: Int? = null,
    val originalLanguage: String? = null,
    val originalName: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null
)