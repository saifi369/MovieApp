package com.starzplay.movieapp.ui

import com.starzplay.movieapp.domain.model.PersonItem
import com.starzplay.movieapp.domain.model.VideoItem
import kotlinx.coroutines.flow.StateFlow

interface IMovieVM {
    val movieList: StateFlow<List<VideoItem>>
    val tvList: StateFlow<List<VideoItem>>
    val personList: StateFlow<List<PersonItem>>
    val viewState: StateFlow<MediaListState>
    fun performSearch(query: String)
}