package com.starzplay.movieapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.starzplay.data.remote.dto.PersonDetailDto
import com.starzplay.data.remote.model.NetworkResult
import com.starzplay.data.repository.ITMDBRepository
import com.starzplay.movieapp.domain.usecase.PersonDetailUseCase
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
class PersonDetailUseCaseTest {
    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @get:Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test fetching movie detail with success`() = runBlocking {

        val mockPerson = PersonDetailDto(name = "Test Person")
        val mockRepo = mockk<ITMDBRepository> {
            coEvery {
                getPersonDetails(1)
            } returns NetworkResult.Success(mockPerson)
        }

        val sut = PersonDetailUseCase(mockRepo)
        sut(1).test {
            //loading state
            awaitItem()
            //data
            Assert.assertEquals(mockPerson.name, awaitItem().data?.name)
            awaitComplete()
        }
        coVerify { mockRepo.getPersonDetails(1) }
    }

    @Test
    fun `test fetching movie detail result with error`() = runBlocking {

        val error = "Some error occurred"
        val mockRepo = mockk<ITMDBRepository> {
            coEvery {
                getPersonDetails(1)
            } returns NetworkResult.Error(mockk {
                coEvery { message } returns error
            })
        }
        val sut = PersonDetailUseCase(mockRepo)
        sut(1).test {
            //loading state
            awaitItem()
            //data
            Assert.assertEquals(error, awaitItem().error)
            awaitComplete()
        }
        coVerify { mockRepo.getPersonDetails(1) }
    }
}