package com.starzplay.data

import com.starzplay.data.remote.dto.CastInfoDto
import com.starzplay.data.util.ReadAssetFile
import com.starzplay.data.util.TMDBSerializer
import kotlinx.serialization.decodeFromString
import org.junit.Assert
import org.junit.Test

class CastInfoTest {

    @Test
    fun `test json response map to dto`() {
        val castInfo = readJson<CastInfoDto>("MockCastResponse.json")

        Assert.assertEquals(castInfo.cast?.size ?: 0, 2)
        Assert.assertEquals(
            "Rocío Pereiras",
            (castInfo.cast?.first()?.name ?: "")
        )
        Assert.assertEquals(
            "Daniel Lema",
            castInfo.cast?.last()?.name ?: ""
        )
    }

    private inline fun <reified T> readJson(fileName: String): T {
        return TMDBSerializer.json.decodeFromString(ReadAssetFile.readFileFromTestResources(fileName))
    }
}