package com.starzplay.data

import com.starzplay.data.remote.TMDBRepository
import com.starzplay.data.remote.TMDBService
import com.starzplay.data.util.CoroutineRule
import com.starzplay.data.util.ReadAssetFile.readJsonFile
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class TMDBRepositoryTest {

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test perform multi search from service`() {
        val mockResponse = Response.success("MockSearchResponse.json".readJsonFile())
        val tmdbService = mockk<TMDBService>()
        coEvery { tmdbService.performMultiSearch("") } returns mockResponse
        val sut = TMDBRepository(tmdbService)

        runTest {
            val actualSearchResult = sut.performMultiSearch("")
            Assert.assertEquals(actualSearchResult.results, mockResponse.body()?.results)
        }
    }
}