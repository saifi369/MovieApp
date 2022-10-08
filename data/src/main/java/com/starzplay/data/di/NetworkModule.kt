package com.starzplay.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.starzplay.data.remote.TMDBService
import com.starzplay.data.util.API_KEY
import com.starzplay.data.util.BASE_URL
import com.starzplay.data.util.TMDBSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideTMDBService(converter: Converter.Factory): TMDBService {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor {
                apiKeyAsQuery(it)
            }
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(converter)
            .build()
            .create(TMDBService::class.java)
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideConverter(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return TMDBSerializer.json.asConverterFactory(contentType)
    }

    @Provides
    fun apiKeyAsQuery(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request()
            .newBuilder()
            .url(
                chain.request().url.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
            )
            .build()
    )
}