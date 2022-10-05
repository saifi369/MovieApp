package com.starzplay.data.remote

import com.starzplay.data.remote.dto.TMDBSearchDto
import com.starzplay.data.remote.model.NetworkResult

interface ITMDBRepository {
    suspend fun performMultiSearch(query: String): NetworkResult<TMDBSearchDto>
}