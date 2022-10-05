package com.starzplay.data.remote

import com.starzplay.data.remote.dto.TMDBSearchDto
import com.starzplay.data.remote.model.NetworkResult

class TMDBRepository(private val tmdbService: TMDBService) : BaseRepository(), ITMDBRepository {
    override suspend fun performMultiSearch(query: String): NetworkResult<TMDBSearchDto> {
        return safeApiCall { tmdbService.performMultiSearch(query) }
    }
}