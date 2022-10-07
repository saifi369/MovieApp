package com.starzplay.movieapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starzplay.data.remote.dto.MediaType
import com.starzplay.data.remote.model.NetworkResult
import com.starzplay.data.repository.ITMDBRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieVM(
    private val movieRepo: ITMDBRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel(), IMovieVM {

    private val _searchResult = MutableStateFlow<List<MediaType>>(emptyList())
    override val searchResult = _searchResult.asStateFlow()

    override fun performSearch(query: String) {
        viewModelScope.launch(dispatcher) {
            when (val response = movieRepo.performMultiSearch(query, false)) {
                is NetworkResult.Success -> {
                    _searchResult.value = response.data.results ?: emptyList()
                }
                is NetworkResult.Error -> {
                    _searchResult.value = emptyList()
                }
            }
        }
    }
}