package com.starzplay.data.remote

import com.starzplay.data.dto.TMDBSearchDto

interface ITMDBRepository {
    suspend fun performMultiSearch(query: String): TMDBSearchDto
}