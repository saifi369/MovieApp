package com.starzplay.movieapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.starzplay.data.remote.dto.MediaTypeMovie
import com.starzplay.data.remote.dto.MediaTypePerson
import com.starzplay.data.remote.dto.MediaTypeTv
import com.starzplay.data.remote.dto.TMDBSearchDto
import com.starzplay.movieapp.domain.model.PersonItem
import com.starzplay.movieapp.domain.model.VideoItem
import com.starzplay.movieapp.util.CoroutineRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class DomainMapperTest {

    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @get:Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test mapping of dto to domain model`() = runTest {
        val dto = TMDBSearchDto(
            results = listOf(
                MediaTypePerson(name = "Test Person"),
                MediaTypeTv(name = "Test Show"),
                MediaTypeMovie(title = "Test Movie")
            )
        )

        val result = dto.asDomainModel()

        Assert.assertEquals((result[0] as PersonItem).name, "Test Person")
        Assert.assertEquals((result[1] as VideoItem).title, "Test Show")
        Assert.assertEquals((result[2] as VideoItem).title, "Test Movie")
    }
}