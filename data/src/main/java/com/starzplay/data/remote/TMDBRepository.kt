package com.starzplay.data.remote

import com.starzplay.data.dto.TMDBSearchDto

class TMDBRepository(private val tmdbService: TMDBService) : ITMDBRepository {
    override suspend fun performMultiSearch(query: String): TMDBSearchDto {
        val response = tmdbService.performMultiSearch(query)
        if (response.isSuccessful)
            return response.body()?.let {
                return@let it
            } ?: TMDBSearchDto()
        else
            return TMDBSearchDto()
    }
}