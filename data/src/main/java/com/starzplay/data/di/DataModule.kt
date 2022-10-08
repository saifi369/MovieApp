package com.starzplay.data.di

import com.starzplay.data.local.ILocalDataSource
import com.starzplay.data.local.LocalDataSource
import com.starzplay.data.repository.ITMDBRepository
import com.starzplay.data.repository.TMDBRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideTmdbRepository(tmdbRepository: TMDBRepository): ITMDBRepository

    @Binds
    abstract fun bindsLocalDataSource(localDataSource: LocalDataSource): ILocalDataSource
}