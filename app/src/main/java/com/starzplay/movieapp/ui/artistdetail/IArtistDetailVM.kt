package com.starzplay.movieapp.ui.artistdetail

import com.starzplay.movieapp.domain.model.PersonDetail
import com.starzplay.movieapp.ui.home.ViewState
import kotlinx.coroutines.flow.StateFlow

interface IArtistDetailVM {
    val personDetail: StateFlow<PersonDetail>
    val viewState: StateFlow<ViewState>
    fun getPersonDetails(personId: Int)
}