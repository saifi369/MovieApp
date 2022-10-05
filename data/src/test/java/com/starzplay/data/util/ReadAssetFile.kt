package com.starzplay.data.util

import com.starzplay.data.dto.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import java.io.*

object ReadAssetFile {

    @Throws(IOException::class)
    fun readJsonFile(fileName: String): String {
        val br =
            BufferedReader(InputStreamReader(FileInputStream(fileName)))
        val sb = StringBuilder()
        var line: String? = br.readLine()
        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }
        return sb.toString()
    }

    @Throws(IOException::class)
    fun readFileFromTestResources(fileName: String): String {
        var inputStream: InputStream? = null
        try {
            inputStream = getInputStreamFromResource(fileName)
            val builder = StringBuilder()
            val reader = BufferedReader(InputStreamReader(inputStream))

            var str: String? = reader.readLine()
            while (str != null) {
                builder.append(str)
                str = reader.readLine()
            }
            return builder.toString()
        } finally {
            inputStream?.close()
        }
    }

    private fun getInputStreamFromResource(fileName: String) =
        javaClass.classLoader?.getResourceAsStream(fileName)

    fun String.readJsonFile(): TMDBSearchDto {
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
        return json.decodeFromString(readFileFromTestResources(this))
    }
}