package com.starzplay.data.repository

import com.starzplay.data.remote.dto.MovieDetailDto
import com.starzplay.data.remote.dto.PersonDetailDto
import com.starzplay.data.remote.dto.TMDBSearchDto
import com.starzplay.data.remote.dto.TvDetailDto
import com.starzplay.data.remote.model.NetworkResult

interface ITMDBRepository {
    suspend fun performMultiSearch(
        query: String,
        isUsingCache: Boolean
    ): NetworkResult<TMDBSearchDto>

    suspend fun getMovieDetails(
        movieId: Int
    ): NetworkResult<MovieDetailDto>

    suspend fun getPersonDetails(
        person: Int
    ): NetworkResult<PersonDetailDto>

    suspend fun getTvDetails(
        tvId: Int
    ): NetworkResult<TvDetailDto>
}