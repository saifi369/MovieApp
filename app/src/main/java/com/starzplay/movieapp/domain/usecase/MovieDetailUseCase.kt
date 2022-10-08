package com.starzplay.movieapp.domain.usecase

import com.starzplay.data.remote.model.NetworkResult
import com.starzplay.data.repository.ITMDBRepository
import com.starzplay.movieapp.domain.DataState
import com.starzplay.movieapp.domain.asDomainModel
import com.starzplay.movieapp.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(private val movieRepository: ITMDBRepository) {

    suspend operator fun invoke(
        movieId: Int
    ): Flow<DataState<MovieDetail>> = flow {
        emit(DataState.Loading())
        when (val response = movieRepository.getMovieDetails(movieId)) {
            is NetworkResult.Success -> {
                val data = response.data.asDomainModel()
                emit(DataState.Success(data = data))
            }
            is NetworkResult.Error -> {
                emit(
                    DataState.Error(
                        response.error.message ?: "Error occurred, Something went wrong"
                    )
                )
            }
        }
    }
}