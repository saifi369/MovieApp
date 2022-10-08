package com.starzplay.data.remote

import com.starzplay.data.remote.ApiURL.ARTIST_DETAIL
import com.starzplay.data.remote.ApiURL.MOVIE_DETAIL
import com.starzplay.data.remote.ApiURL.MULTI_SEARCH
import com.starzplay.data.remote.ApiURL.TV_DETAIL
import com.starzplay.data.remote.dto.MovieDetailDto
import com.starzplay.data.remote.dto.PersonDetailDto
import com.starzplay.data.remote.dto.TMDBSearchDto
import com.starzplay.data.remote.dto.TvDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {

    @GET(MULTI_SEARCH)
    suspend fun performMultiSearch(
        @Query("query") query: String
    ): Response<TMDBSearchDto>

    @GET(MOVIE_DETAIL)
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): Response<MovieDetailDto>

    @GET(TV_DETAIL)
    suspend fun getTvDetail(@Path("tvId") tvId: Int): Response<TvDetailDto>

    @GET(ARTIST_DETAIL)
    suspend fun getPersonDetail(@Path("personId") personId: Int): Response<PersonDetailDto>
}