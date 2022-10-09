package com.starzplay.movieapp.presentation.ui.videodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starzplay.movieapp.domain.DataState
import com.starzplay.movieapp.domain.model.CastInfo
import com.starzplay.movieapp.domain.model.MovieDetail
import com.starzplay.movieapp.domain.model.TvDetail
import com.starzplay.movieapp.domain.usecase.CastDetailUseCase
import com.starzplay.movieapp.domain.usecase.MovieDetailUseCase
import com.starzplay.movieapp.domain.usecase.TvDetailUseCase
import com.starzplay.movieapp.presentation.ui.home.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoDetailVM @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val getMovieDetailUseCase: MovieDetailUseCase,
    private val getTvDetailUseCase: TvDetailUseCase,
    private val castDetailUseCase: CastDetailUseCase
) : ViewModel(), IVideoDetailVM {

    private val _movieDetail = MutableStateFlow(MovieDetail())
    override val movieDetail: StateFlow<MovieDetail> = _movieDetail.asStateFlow()

    private val _tvDetail = MutableStateFlow(TvDetail())
    override val tvDetail: StateFlow<TvDetail> = _tvDetail.asStateFlow()

    private val _castDetail = MutableStateFlow<List<CastInfo>>(emptyList())
    override val castDetail: StateFlow<List<CastInfo>> = _castDetail.asStateFlow()

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    override val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    override fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(dispatcher) {
            getMovieDetailUseCase(movieId).onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _viewState.value = ViewState(isLoading = true)
                    }
                    is DataState.Success -> {
                        dataState.data?.let {
                            _movieDetail.value = it
                        }
                        _viewState.value = ViewState(isSuccess = true)
                    }
                    is DataState.Error -> {
                        _viewState.value =
                            ViewState(error = "Something went wrong, Please check your internet connection.\n${dataState.error}")
                    }
                }
            }.launchIn(CoroutineScope(dispatcher))
        }
    }

    override fun getTvDetails(tvId: Int) {
        viewModelScope.launch(dispatcher) {
            getTvDetailUseCase(tvId).onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _viewState.value = ViewState(isLoading = true)
                    }
                    is DataState.Success -> {
                        dataState.data?.let {
                            _tvDetail.value = it
                        }
                        _viewState.value = ViewState(isSuccess = true)
                    }
                    is DataState.Error -> {
                        _viewState.value =
                            ViewState(error = "Something went wrong, Please check your internet connection.\n${dataState.error}")
                    }
                }
            }.launchIn(CoroutineScope(dispatcher))
        }
    }

    override fun getCastDetails(mediaType: String, mediaId: Int) {
        viewModelScope.launch(dispatcher) {
            castDetailUseCase(mediaType, mediaId).onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _viewState.value = ViewState(isLoading = true)
                    }
                    is DataState.Success -> {
                        dataState.data?.let {
                            _castDetail.value = it.filterNotNull()
                        }
                        _viewState.value = ViewState(isSuccess = true)
                    }
                    is DataState.Error -> {
                        _viewState.value =
                            ViewState(error = "Something went wrong, Please check your internet connection.\n${dataState.error}")
                    }
                }
            }.launchIn(CoroutineScope(dispatcher))
        }
    }
}