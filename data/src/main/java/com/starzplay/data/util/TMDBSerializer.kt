package com.starzplay.data.util

import com.starzplay.data.remote.dto.MediaType
import com.starzplay.data.remote.dto.MediaTypeMovie
import com.starzplay.data.remote.dto.MediaTypePerson
import com.starzplay.data.remote.dto.MediaTypeTv
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

object TMDBSerializer {
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
}