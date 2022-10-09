package com.starzplay.movieapp.presentation.ui.artistdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starzplay.movieapp.domain.DataState
import com.starzplay.movieapp.domain.model.PersonDetail
import com.starzplay.movieapp.domain.usecase.PersonDetailUseCase
import com.starzplay.movieapp.presentation.ui.home.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailVM @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val getPersonDetailUseCase: PersonDetailUseCase
) : ViewModel(), IArtistDetailVM {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    override val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    private val _personDetail: MutableStateFlow<PersonDetail> = MutableStateFlow(PersonDetail())
    override val personDetail: StateFlow<PersonDetail> = _personDetail.asStateFlow()

    override fun getPersonDetails(personId: Int) {
        viewModelScope.launch(dispatcher) {
            getPersonDetailUseCase(personId = personId).onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _viewState.value = ViewState(isLoading = true)
                    }
                    is DataState.Success -> {
                        dataState.data?.let {
                            _personDetail.value = it
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