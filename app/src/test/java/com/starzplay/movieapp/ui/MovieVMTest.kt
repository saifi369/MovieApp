package com.starzplay.movieapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.starzplay.data.remote.dto.MediaType
import com.starzplay.data.remote.dto.MediaTypeMovie
import com.starzplay.data.remote.dto.MediaTypePerson
import com.starzplay.data.remote.dto.MediaTypeTv
import com.starzplay.data.remote.model.NetworkResult
import com.starzplay.data.repository.TMDBRepository
import com.starzplay.movieapp.util.CoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieVMTest {

    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @get:Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: MovieVM

    @Test
    fun `test to fetch search results from repository with success`() = runTest {
        val resultList = listOf(
            MediaTypeMovie(),
            MediaTypeTv(),
            MediaTypePerson()
        )
        val mockRepo = mockk<TMDBRepository> {
            coEvery {
                performMultiSearch("", false)
            } returns NetworkResult.Success(mockk {
                coEvery { results } returns resultList
            })
        }
        sut = MovieVM(mockRepo, UnconfinedTestDispatcher())
        sut.performSearch("")
        sut.searchResult.test {
            assertEquals(resultList, awaitItem())
        }
        coVerify { mockRepo.performMultiSearch("", false) }
    }

    @Test
    fun `test to fetch search results from repository with error`() = runTest {
        val error = "This request unfortunately failed please try again"
        val mockRepo = mockk<TMDBRepository> {
            coEvery {
                performMultiSearch("", false)
            } returns NetworkResult.Error(mockk{
                coEvery { message } returns error
            })
        }
        sut = MovieVM(mockRepo, UnconfinedTestDispatcher())
        sut.performSearch("")
        sut.searchResult.test {
            assertEquals(listOf<MediaType>(), awaitItem())
        }
        coVerify { mockRepo.performMultiSearch("", false) }
    }
}