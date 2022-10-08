package com.starzplay.data.repository

import com.starzplay.data.local.ILocalDataSource
import com.starzplay.data.remote.TMDBService
import com.starzplay.data.remote.dto.TMDBSearchDto
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
            if (response is NetworkResult.Success && response.data.results?.isNotEmpty() == true)
                localDataSource.saveSearchResult(response.data)
            return response
        }
    }
}