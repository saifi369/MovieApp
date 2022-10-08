package com.starzplay.movieapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.starzplay.data.remote.dto.MovieDetailDto
import com.starzplay.data.remote.model.NetworkResult
import com.starzplay.data.repository.ITMDBRepository
import com.starzplay.movieapp.domain.usecase.MovieDetailUseCase
import com.starzplay.movieapp.util.CoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailUseCaseTest {
    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @get:Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test fetching movie detail with success`() = runBlocking {

        val mockMovie = MovieDetailDto(title = "Test Movie")
        val mockRepo = mockk<ITMDBRepository> {
            coEvery {
                getMovieDetails(1)
            } returns NetworkResult.Success(mockMovie)
        }

        val sut = MovieDetailUseCase(mockRepo)
        sut(1).test {
            //loading state
            awaitItem()
            //data
            Assert.assertEquals(mockMovie.title, awaitItem().data?.title)
            awaitComplete()
        }
        coVerify { mockRepo.getMovieDetails(1) }
    }

    @Test
    fun `test fetching movie detail result with error`() = runBlocking {

        val error = "Some error occurred"
        val mockRepo = mockk<ITMDBRepository> {
            coEvery {
                getMovieDetails(1)
            } returns NetworkResult.Error(mockk {
                coEvery { message } returns error
            })
        }
        val sut = MovieDetailUseCase(mockRepo)
        sut(1).test {
            //loading state
            awaitItem()
            //data
            Assert.assertEquals(error, awaitItem().error)
            awaitComplete()
        }
        coVerify { mockRepo.getMovieDetails(1) }
    }
}