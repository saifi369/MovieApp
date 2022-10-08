package com.starzplay.data.local

import android.content.Context
import com.starzplay.data.remote.dto.TMDBSearchDto
import com.starzplay.data.util.SHARED_PREFERENCES_FILE_NAME
import com.starzplay.data.util.TMDBSerializer
import com.starzplay.data.util.TMDB_RESULT_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import javax.inject.Inject

class LocalDataSource @Inject constructor(@ApplicationContext private val context: Context) :
    ILocalDataSource {

    override fun getSearchResult(): TMDBSearchDto? {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0)
        val jsonString = sharedPref.getString(TMDB_RESULT_KEY, null)
        return jsonString?.let {
            return@let TMDBSerializer.json.decodeFromJsonElement(Json.parseToJsonElement(it))
        }
    }

    override fun saveSearchResult(results: TMDBSearchDto): Boolean {
        val jsonString = TMDBSerializer.json.encodeToString(results)
        return context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0).edit()
            .putString(TMDB_RESULT_KEY, jsonString)
            .commit()
    }
}