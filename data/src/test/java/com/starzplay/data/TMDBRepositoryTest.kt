package com.starzplay.data

import com.starzplay.data.remote.TMDBRepository
import com.starzplay.data.remote.TMDBService
import com.starzplay.data.remote.dto.TMDBSearchDto
import com.starzplay.data.remote.model.NetworkResult
import com.starzplay.data.util.CoroutineRule
import com.starzplay.data.util.ReadAssetFile.readJsonFile
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class TMDBRepositoryTest {

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `test perform multi search from service`() {
        val mockResponse = Response.success("MockSearchResponse.json".readJsonFile())
        val tmdbService = mockk<TMDBService>()
        coEvery { tmdbService.performMultiSearch("") } returns mockResponse
        val sut = TMDBRepository(tmdbService)

        runTest {
            val actualSearchResult = sut.performMultiSearch("") as NetworkResult.Success
            Assert.assertEquals(actualSearchResult.data.results, mockResponse.body()?.results)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test perform multi search from service is failed`() {
        val errorMessage = "This request unfortunately failed please try again"
        val mockErrorResponse = Response.error<TMDBSearchDto>(
            400,
            errorMessage.toResponseBody("application/json".toMediaType())
        )
        val tmdbService = mockk<TMDBService>()
        coEvery { tmdbService.performMultiSearch("") } returns mockErrorResponse
        val sut = TMDBRepository(tmdbService)

        runTest {
            val actualSearchResult = sut.performMultiSearch("") as NetworkResult.Error
            Assert.assertEquals(
                actualSearchResult.error.message,
                mockErrorResponse.errorBody()?.string()
            )
        }
    }
}