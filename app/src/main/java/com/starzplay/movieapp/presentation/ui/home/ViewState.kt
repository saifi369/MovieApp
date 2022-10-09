package com.starzplay.movieapp.presentation.ui.home

data class ViewState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)