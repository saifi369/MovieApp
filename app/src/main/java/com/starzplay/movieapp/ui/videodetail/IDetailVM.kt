package com.starzplay.movieapp.ui.videodetail

import com.starzplay.movieapp.domain.model.MovieDetail
import com.starzplay.movieapp.domain.model.TvDetail
import com.starzplay.movieapp.ui.home.ViewState
import kotlinx.coroutines.flow.StateFlow

interface IDetailVM {
    val movieDetail: StateFlow<MovieDetail>
    val tvDetail: StateFlow<TvDetail>
    val viewState: StateFlow<ViewState>
    fun getMovieDetails(movieId: Int)
    fun getTvDetails(tvId: Int)
}