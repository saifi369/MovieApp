package com.starzplay.movieapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.starzplay.data.remote.dto.TvDetailDto
import com.starzplay.data.remote.model.NetworkResult
import com.starzplay.data.repository.ITMDBRepository
import com.starzplay.movieapp.domain.usecase.TvDetailUseCase
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
class TvDetailUseCaseTest {
    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @get:Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test fetching tv detail with success`() = runBlocking {

        val mockTv = TvDetailDto(name = "Test Tv Show")
        val mockRepo = mockk<ITMDBRepository> {
            coEvery {
                getTvDetails(1)
            } returns NetworkResult.Success(mockTv)
        }

        val sut = TvDetailUseCase(mockRepo)
        sut(1).test {
            //loading state
            awaitItem()
            //data
            Assert.assertEquals(mockTv.name, awaitItem().data?.name)
            awaitComplete()
        }
        coVerify { mockRepo.getTvDetails(1) }
    }

    @Test
    fun `test fetching tv detail result with error`() = runBlocking {

        val error = "Some error occurred"
        val mockRepo = mockk<ITMDBRepository> {
            coEvery {
                getTvDetails(1)
            } returns NetworkResult.Error(mockk {
                coEvery { message } returns error
            })
        }
        val sut = TvDetailUseCase(mockRepo)
        sut(1).test {
            //loading state
            awaitItem()
            //data
            Assert.assertEquals(error, awaitItem().error)
            awaitComplete()
        }
        coVerify { mockRepo.getTvDetails(1) }
    }
}