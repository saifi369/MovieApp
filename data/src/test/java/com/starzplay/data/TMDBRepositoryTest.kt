package com.starzplay.data

import android.content.Context
import android.content.SharedPreferences
import com.starzplay.data.local.LocalDataSource
import com.starzplay.data.repository.TMDBRepository
import com.starzplay.data.remote.TMDBService
import com.starzplay.data.remote.dto.TMDBSearchDto
import com.starzplay.data.remote.model.NetworkResult
import com.starzplay.data.util.CoroutineRule
import com.starzplay.data.util.ReadAssetFile
import com.starzplay.data.util.ReadAssetFile.readJsonFile
import com.starzplay.data.util.SHARED_PREFERENCES_FILE_NAME
import com.starzplay.data.util.TMDB_RESULT_KEY
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
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
    fun `test perform multi search from service`() = runTest {
        val mockResponse = Response.success("MockSearchResponse.json".readJsonFile())
        val tmdbService = mockk<TMDBService>()
        val mockLocalData = mockk<LocalDataSource>()

        every { mockLocalData.saveSearchResult(mockResponse.body()!!) } returns true
        every { mockLocalData.getSearchResult() } returns null
        coEvery { tmdbService.performMultiSearch("") } returns mockResponse

        val sut = TMDBRepository(tmdbService, mockLocalData)


        val actualSearchResult = sut.performMultiSearch("", false) as NetworkResult.Success
        Assert.assertEquals(actualSearchResult.data.results, mockResponse.body()?.results)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test perform multi search from service is failed`() = runTest {
        val errorMessage = "This request unfortunately failed please try again"
        val mockErrorResponse = Response.error<TMDBSearchDto>(
            400,
            errorMessage.toResponseBody("application/json".toMediaType())
        )
        val tmdbService = mockk<TMDBService>()
        val mockLocalData = mockk<LocalDataSource>()
        every { mockLocalData.getSearchResult() } returns null
        coEvery { tmdbService.performMultiSearch("") } returns mockErrorResponse
        val sut = TMDBRepository(tmdbService, mockLocalData)


        val actualSearchResult = sut.performMultiSearch("", false) as NetworkResult.Error
        Assert.assertEquals(
            actualSearchResult.error.message,
            mockErrorResponse.errorBody()?.string()
        )

    }

    @Test
    fun `test fetch results from cache`() = runTest {
        val jsonFile = "MockSearchResponse.json".readJsonFile()
        val mockResponse = Response.success(jsonFile)
        val mockContext = mockk<Context>()
        val tmdbMockService = mockk<TMDBService>()
        val mockLocalData = mockk<LocalDataSource>()

        every { mockLocalData.getSearchResult() } returns null
        every { mockLocalData.saveSearchResult(mockResponse.body()!!) }

        val sut = TMDBRepository(tmdbMockService, LocalDataSource(mockContext))

        val sharedPref = mockk<SharedPreferences>()

        val mockSearchResult = ReadAssetFile.tmdbSerializer.encodeToString(jsonFile)
        every {
            mockContext.getSharedPreferences(
                SHARED_PREFERENCES_FILE_NAME,
                0
            )
        } returns sharedPref
        every { sharedPref.getString(TMDB_RESULT_KEY, null) } returns mockSearchResult


        val expectedResult = NetworkResult.Success(jsonFile)
        val actualResult =
            sut.performMultiSearch("", isUsingCache = true) as NetworkResult.Success
        Assert.assertEquals(expectedResult.data.results, actualResult.data.results)

    }

    @Test
    fun `test fetch results from cache on second call`() = runTest {
        val jsonFile = "MockSearchResponse.json".readJsonFile()
        val mockResponse = Response.success(jsonFile)
        val tmdbMockService = mockk<TMDBService>()
        val mockLocalData = mockk<LocalDataSource>()

        //first time cache access
        every { mockLocalData.getSearchResult() } returns null
        every { mockLocalData.saveSearchResult(jsonFile) } returns true
        coEvery { tmdbMockService.performMultiSearch("") } returns mockResponse

        val sut = TMDBRepository(tmdbMockService, mockLocalData)


        val expectedResult =
            NetworkResult.Success(jsonFile)
        val actualResultOnFirstCall =
            sut.performMultiSearch(isUsingCache = true, query = "") as NetworkResult.Success

        //second time cache access
        every { mockLocalData.getSearchResult() } returns actualResultOnFirstCall.data

        val actualResultOnSecondCall =
            sut.performMultiSearch(isUsingCache = true, query = "") as NetworkResult.Success

        Assert.assertEquals(
            expectedResult.data.results,
            actualResultOnFirstCall.data.results
        )

        Assert.assertEquals(
            expectedResult.data.results,
            actualResultOnSecondCall.data.results
        )

    }
}