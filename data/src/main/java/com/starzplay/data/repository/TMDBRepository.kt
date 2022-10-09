package com.starzplay.data.repository

import com.starzplay.data.local.ILocalDataSource
import com.starzplay.data.remote.TMDBService
import com.starzplay.data.remote.dto.*
import com.starzplay.data.remote.model.NetworkResult
import javax.inject.Inject

class TMDBRepository @Inject constructor(
    private val remoteService: TMDBService,
    private val localDataSource: ILocalDataSource
) : BaseRepository(), ITMDBRepository {
    override suspend fun performMultiSearch(
        query: String,
        isUsingCache: Boolean
    ): NetworkResult<TMDBSearchDto> {
        return if (isUsingCache && localDataSource.getSearchResult() != null) {
            NetworkResult.Success(data = localDataSource.getSearchResult()!!)
        } else {
            val response = safeApiCall { remoteService.performMultiSearch(query) }
            if ((response is NetworkResult.Success) && (response.data.results?.isNotEmpty() == true))
                localDataSource.saveSearchResult(response.data)
            return response
        }
    }

    override suspend fun getMovieDetails(movieId: Int): NetworkResult<MovieDetailDto> =
        safeApiCall { remoteService.getMovieDetail(movieId) }

    override suspend fun getPersonDetails(person: Int): NetworkResult<PersonDetailDto> =
        safeApiCall { remoteService.getPersonDetail(person) }

    override suspend fun getTvDetails(tvId: Int): NetworkResult<TvDetailDto> =
        safeApiCall { remoteService.getTvDetail(tvId) }

    override suspend fun getCastDetail(
        mediaType: String,
        mediaId: Int
    ): NetworkResult<CastInfoDto> {
        return if (mediaType == "movie")
            safeApiCall { remoteService.getMovieCast(mediaId) }
        else
            safeApiCall { remoteService.getShowCast(mediaId) }
    }
}