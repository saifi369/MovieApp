package com.starzplay.movieapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.starzplay.data.remote.dto.MediaTypeMovie
import com.starzplay.data.remote.dto.MediaTypePerson
import com.starzplay.data.remote.dto.MediaTypeTv
import com.starzplay.data.remote.model.NetworkResult
import com.starzplay.data.repository.TMDBRepository
import com.starzplay.movieapp.domain.model.PersonItem
import com.starzplay.movieapp.domain.model.VideoItem
import com.starzplay.movieapp.util.CoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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

    //test passed for state flow, needs refactoring for flow collect mechanism
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
//        sut = MovieVM(mockRepo, UnconfinedTestDispatcher())
        sut.performSearch("")
//        sut.searchResult.test {
//            assertEquals(resultList, awaitItem())
//        }
        coVerify { mockRepo.performMultiSearch("", false) }
    }

    //test passed for state flow, needs refactoring for flow collect mechanism
    @Test
    fun `test to fetch search results from repository with error`() = runTest {
        val error = "This request unfortunately failed please try again"
        val mockRepo = mockk<TMDBRepository> {
            coEvery {
                performMultiSearch("", false)
            } returns NetworkResult.Error(mockk {
                coEvery { message } returns error
            })
        }
//        sut = MovieVM(mockRepo, UnconfinedTestDispatcher())
        sut.performSearch("")
//        sut.searchResult.test {
//            assertEquals(listOf<MediaType>(), awaitItem())
//        }
        coVerify { mockRepo.performMultiSearch("", false) }
    }

    @Test
    fun `test filtering search results by media type`() = runBlocking {
        val resultList = listOf(
            VideoItem(mediaType = "movie", title = "Interstellar"),
            VideoItem(mediaType = "tv", title = "Modern Family"),
            PersonItem(mediaType = "person", name = "Sandra Bullock")
        )

        sut = MovieVM(mockk(), mockk())
        sut.filterMediaTypes(resultList)

        sut.movieList.test {
            assertEquals(listOf(resultList[0]), awaitItem())
        }
        sut.tvList.test {
            assertEquals(listOf(resultList[1]), awaitItem())
        }
        sut.personList.test {
            assertEquals(listOf(resultList[2]), awaitItem())
        }
        sut.viewState.test {
            assertEquals(MediaListState(isLoading = false, isSuccess = true), awaitItem())
        }
    }

    @Test
    fun `test search result ordering alphabetically`() = runBlocking {
        val resultList = listOf(
            VideoItem(mediaType = "movie", title = "Interstellar"),
            VideoItem(mediaType = "movie", title = "Dune"),
            VideoItem(mediaType = "tv", title = "The Witcher"),
            VideoItem(mediaType = "tv", title = "Modern Family"),
            PersonItem(mediaType = "person", name = "Sandra Bullock"),
            PersonItem(mediaType = "person", name = "Adam Sandler")
        )

        sut = MovieVM(mockk(), mockk())
        sut.filterMediaTypes(resultList)

        sut.movieList.test {
            val list = awaitItem()
            assert((list.first().title!!) < (list[1].title!!))
        }
        sut.personList.test {
            val list = awaitItem()
            assert(list.first().name!! < list[1].name!!)
        }
        sut.tvList.test {
            val list = awaitItem()
            assert(list.first().title!! < list[1].title!!)
        }
        sut.viewState.test {
            assertEquals(MediaListState(isLoading = false, isSuccess = true), awaitItem())
        }
    }
}