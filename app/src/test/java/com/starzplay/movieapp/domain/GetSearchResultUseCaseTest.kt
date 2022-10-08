package com.starzplay.movieapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.starzplay.data.remote.dto.MediaTypeMovie
import com.starzplay.data.remote.dto.MediaTypePerson
import com.starzplay.data.remote.dto.MediaTypeTv
import com.starzplay.data.remote.model.NetworkResult
import com.starzplay.data.repository.ITMDBRepository
import com.starzplay.movieapp.domain.model.MediaItem
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
class GetSearchResultUseCaseTest {
    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @get:Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test fetching search result with success`() = runBlocking {

        val resultList = listOf(
            MediaTypeMovie(title = "Test Movie"),
            MediaTypeTv(name = "Test Show"),
            MediaTypePerson(name = "Test Person")
        )
        val mockRepo = mockk<ITMDBRepository> {
            coEvery {
                performMultiSearch("", false)
            } returns NetworkResult.Success(mockk {
                coEvery { results } returns resultList
            })
        }
        val sut = GetSearchResultUseCase(mockRepo)
        sut("", false).test {
            //loading state
            Assert.assertEquals(DataState.Loading<MediaItem>().data, awaitItem().data)
            //data
            Assert.assertEquals(resultList.size, awaitItem().data?.size ?: 9)
            awaitComplete()
        }
        coVerify { mockRepo.performMultiSearch("", false) }
    }

    @Test
    fun `test fetching search result with error`() = runBlocking {

        val error = "Some error occurred"
        val mockRepo = mockk<ITMDBRepository> {
            coEvery {
                performMultiSearch("", false)
            } returns NetworkResult.Error(mockk {
                coEvery { message } returns error
            })
        }
        val sut = GetSearchResultUseCase(mockRepo)
        sut("", false).test {
            //loading state
            Assert.assertEquals(DataState.Loading<MediaItem>().data, awaitItem().data)
            //data
            Assert.assertEquals(error, awaitItem().error)
            awaitComplete()
        }
        coVerify { mockRepo.performMultiSearch("", false) }
    }
}