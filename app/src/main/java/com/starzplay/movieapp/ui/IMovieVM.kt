package com.starzplay.movieapp.ui

import com.starzplay.data.remote.dto.MediaType
import kotlinx.coroutines.flow.StateFlow

interface IMovieVM {
    val searchResult : StateFlow<List<MediaType>>
    fun performSearch(query:String)
}