package com.starzplay.movieapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starzplay.movieapp.domain.DataState
import com.starzplay.movieapp.domain.GetSearchResultUseCase
import com.starzplay.movieapp.domain.model.MediaItem
import com.starzplay.movieapp.domain.model.PersonItem
import com.starzplay.movieapp.domain.model.VideoItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieVM(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val getSearchResultUseCase: GetSearchResultUseCase
) : ViewModel(), IMovieVM {

    private val _movieList = MutableStateFlow<List<VideoItem>>(emptyList())
    override val movieList = _movieList.asStateFlow()

    private val _tvList = MutableStateFlow<List<VideoItem>>(emptyList())
    override val tvList = _tvList.asStateFlow()

    private val _personList: MutableStateFlow<List<PersonItem>> = MutableStateFlow(emptyList())
    override val personList: StateFlow<List<PersonItem>> = _personList.asStateFlow()

    private val _viewState: MutableStateFlow<MediaListState> = MutableStateFlow(MediaListState())
    override val viewState: StateFlow<MediaListState> = _viewState.asStateFlow()

    override fun performSearch(query: String) {
        viewModelScope.launch(dispatcher) {
            getSearchResultUseCase(query, false).onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _viewState.value = MediaListState(isLoading = true)
                    }
                    is DataState.Success -> {
                        filterMediaTypes(dataState.data)
                    }
                    is DataState.Error -> {
                        clearLists()
                        _viewState.value = MediaListState(error = dataState.error)
                    }
                }
            }.launchIn(CoroutineScope(dispatcher))
        }
    }

    private fun clearLists() {
        _movieList.value = emptyList()
        _tvList.value = emptyList()
        _personList.value = emptyList()
    }

    fun filterMediaTypes(list: List<MediaItem?>?) {
        list?.let {
            _movieList.value = getMediaTypeList<VideoItem>(list, "movie").sortedBy {
                it.title
            }
            _tvList.value = getMediaTypeList<VideoItem>(list, "tv").sortedBy {
                it.title
            }
            _personList.value = getMediaTypeList<PersonItem>(list, "person").sortedBy {
                it.name
            }
        }
        _viewState.value = MediaListState(isSuccess = true)
    }

    private inline fun <reified T : MediaItem> getMediaTypeList(
        list: List<MediaItem?>,
        mediaType: String
    ): List<T> {
        return list.filter { it?.mediaType == mediaType }.filterIsInstance<T>()
    }
}