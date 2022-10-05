package com.starzplay.data

import com.starzplay.data.dto.MediaTypeMovie
import com.starzplay.data.dto.MediaTypePerson
import com.starzplay.data.util.ReadAssetFile.readJsonFile
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
}