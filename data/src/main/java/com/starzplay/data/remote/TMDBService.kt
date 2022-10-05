package com.starzplay.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.starzplay.data.dto.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {

    @GET("search/multi")
    suspend fun performMultiSearch(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<TMDBSearchDto>

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val API_KEY = "4796a8a8fb4ef004d575df887b04bd34"

        private val json = Json {
            serializersModule = SerializersModule {
                ignoreUnknownKeys = true
                polymorphic(MediaType::class) {
                    subclass(MediaTypeMovie::class, MediaTypeMovie.serializer())
                    subclass(MediaTypeTv::class, MediaTypeTv.serializer())
                    subclass(MediaTypePerson::class, MediaTypePerson.serializer())
                }
            }
        }

        fun create(): TMDBService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
                .create(TMDBService::class.java)
        }
    }

}