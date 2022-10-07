package com.starzplay.data.repository

import com.starzplay.data.remote.dto.TMDBSearchDto
import com.starzplay.data.remote.model.NetworkResult

interface ITMDBRepository {
    suspend fun performMultiSearch(
        query: String,
        isUsingCache: Boolean
    ): NetworkResult<TMDBSearchDto>
}