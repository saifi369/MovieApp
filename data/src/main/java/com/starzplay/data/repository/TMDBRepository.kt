package com.starzplay.data.repository

import com.starzplay.data.local.ILocalDataSource
import com.starzplay.data.remote.TMDBService
import com.starzplay.data.remote.dto.TMDBSearchDto
import com.starzplay.data.remote.model.NetworkResult

class TMDBRepository(
    private val remoteService: TMDBService,
    private val localDataSource: ILocalDataSource
) : BaseRepository(), ITMDBRepository {
    override suspend fun performMultiSearch(
        query: String,
        isUsingCache: Boolean
    ): NetworkResult<TMDBSearchDto> {
        val localResult = localDataSource.getSearchResult()
        return if (isUsingCache && localResult != null) {
            NetworkResult.Success(data = localResult)
        } else {
            val response = safeApiCall { remoteService.performMultiSearch(query) }
            if (response is NetworkResult.Success)
                localDataSource.saveSearchResult(response.data)
            return response
        }
    }
}