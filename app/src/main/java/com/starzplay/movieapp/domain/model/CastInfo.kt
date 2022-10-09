package com.starzplay.movieapp.domain.model

data class CastInfo(
    val castId: Int? = null,
    val character: String? = null,
    val mediaType: String = "person",
    val gender: Int? = null,
    val id: Int? = null,
    val knownForDepartment: String? = null,
    val name: String? = null,
    val originalName: String? = null,
    val popularity: Double? = null,
    val profilePath: String? = null
) {
    var fullProfileUrl: String? = "https://image.tmdb.org/t/p/w500${profilePath}"
}