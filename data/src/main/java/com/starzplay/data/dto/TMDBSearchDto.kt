package com.starzplay.data.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
data class TMDBSearchDto(
    @SerialName("page")
    val page: Int? = null,
    @SerialName("results")
    val results: List<MediaType>? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("total_results")
    val totalResults: Int? = null
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("media_type")
abstract class MediaType(
    @SerialName("media_type")
    val contentMediaType: String? = null,
    @SerialName("id")
    val id: Int? = null
)

@Serializable
abstract class MediaTypeVideo(
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int?>? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null,
) : MediaType()

@Serializable
@SerialName("tv")
data class MediaTypeTv(
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("origin_country")
    val originCountry: List<String?>? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("popularity")
    val popularity: Double? = null,
) : MediaTypeVideo()

@SerialName("movie")
@Serializable
data class MediaTypeMovie(
    @SerialName("adult")
    val adult: Boolean? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("video")
    val video: Boolean? = null,
) : MediaTypeVideo()

@Serializable
@SerialName("person")
data class MediaTypePerson(
    @SerialName("adult")
    val adult: Boolean? = null,
    @SerialName("gender")
    val gender: Int? = null,
    @SerialName("known_for")
    val knownFor: List<MediaType?>? = null,
    @SerialName("known_for_department")
    val knownForDepartment: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("popularity")
    val popularity: Double? = null,
    @SerialName("profile_path")
    val profilePath: String? = null
) : MediaType()
