package com.starzplay.data

import com.starzplay.data.remote.dto.MovieDetailDto
import com.starzplay.data.remote.dto.PersonDetailDto
import com.starzplay.data.remote.dto.TvDetailDto
import com.starzplay.data.util.ReadAssetFile
import com.starzplay.data.util.TMDBSerializer
import kotlinx.serialization.decodeFromString
import org.junit.Assert
import org.junit.Test

class DetailResponseTest {

    @Test
    fun `test movie detail json response map to dto`() {
        val movieDetail = readJson<MovieDetailDto>("MockMovieDetailResponse.json")

        Assert.assertEquals("Sandra Brown's White Hot", movieDetail.title)
        Assert.assertEquals(3, movieDetail.genres?.size ?: 0)
        Assert.assertEquals("Mystery", movieDetail.genres?.get(2)?.name ?: "")
    }

    @Test
    fun `test tv detail json response map to dto`() {
        val tvDetail = readJson<TvDetailDto>("MockTvDetailResponse.json")

        Assert.assertEquals("Grey's Anatomy", tvDetail.name)
        Assert.assertEquals(1, tvDetail.genres?.size ?: 0)
        Assert.assertEquals("Drama", tvDetail.genres?.get(0)?.name ?: "")
    }

    @Test
    fun `test person detail json response map to dto`() {
        val personDetailDto = readJson<PersonDetailDto>("MockPersonDetailResponse.json")

        Assert.assertEquals("Sandra Oh", personDetailDto.name)
        Assert.assertEquals(9, personDetailDto.alsoKnownAs?.size ?: 0)
        Assert.assertEquals("산드라 오", personDetailDto.alsoKnownAs?.get(0) ?: "")
    }

    private inline fun <reified T> readJson(fileName: String): T {
        return TMDBSerializer.json.decodeFromString(ReadAssetFile.readFileFromTestResources(fileName))
    }
}