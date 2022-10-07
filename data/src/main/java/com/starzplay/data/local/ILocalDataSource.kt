package com.starzplay.data.local

import com.starzplay.data.remote.dto.TMDBSearchDto

interface ILocalDataSource {
    fun saveSearchResult(results: TMDBSearchDto): Boolean
    fun getSearchResult(): TMDBSearchDto?
}