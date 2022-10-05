package com.starzplay.data

import com.starzplay.data.dto.*
import com.starzplay.data.util.ReadAssetFile
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.junit.Assert
import org.junit.Test

class SearchResponseTest {

    @Test
    fun `test json response map to dto`() {
        val searchResult = "MockSearchResponse.json".readJsonFile()

        Assert.assertEquals(searchResult.results?.size ?: 0, 20)
        Assert.assertEquals(
            "Sandra Bullock",
            (searchResult.results?.first() as MediaTypePerson).name ?: ""
        )
        Assert.assertEquals(
            "Sandra",
            (searchResult.results?.last() as MediaTypeMovie).title ?: ""
        )
    }

    private fun String.readJsonFile(): TMDBSearchDto {
        val json = Json {
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                polymorphic(MediaType::class) {
                    subclass(MediaTypeMovie::class, MediaTypeMovie.serializer())
                    subclass(MediaTypeTv::class, MediaTypeTv.serializer())
                    subclass(MediaTypePerson::class, MediaTypePerson.serializer())
                }
            }
        }
        return json.decodeFromString(ReadAssetFile.readFileFromTestResources(this))
    }
}