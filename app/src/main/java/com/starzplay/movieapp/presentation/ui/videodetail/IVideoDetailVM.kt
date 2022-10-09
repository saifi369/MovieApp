package com.starzplay.movieapp.presentation.ui.videodetail

import com.starzplay.movieapp.domain.model.CastInfo
import com.starzplay.movieapp.domain.model.MovieDetail
import com.starzplay.movieapp.domain.model.TvDetail
import com.starzplay.movieapp.presentation.ui.home.ViewState
import kotlinx.coroutines.flow.StateFlow

interface IVideoDetailVM {
    val movieDetail: StateFlow<MovieDetail>
    val tvDetail: StateFlow<TvDetail>
    val castDetail: StateFlow<List<CastInfo>>
    val viewState: StateFlow<ViewState>
    fun getMovieDetails(movieId: Int)
    fun getTvDetails(tvId: Int)
    fun getCastDetails(mediaType: String, mediaId: Int)
}