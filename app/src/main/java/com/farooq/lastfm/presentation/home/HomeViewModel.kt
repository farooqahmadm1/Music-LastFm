package com.farooq.lastfm.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farooq.core.domain.ProgressBarState
import com.farooq.core.domain.Resource
import com.farooq.core.domain.UIComponent
import com.farooq.lastfm.domain.model.Album
import com.farooq.lastfm.domain.use_cases.GetAlbums
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAlbums: GetAlbums
) : ViewModel() {

    private val _state = MutableStateFlow(UIState())
    var state: StateFlow<UIState> = _state

    private val _uiState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState.Nothing)
    var uiState: StateFlow<HomeUIState> = _uiState

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetAlbums -> getAlbums()
        }
    }

    private fun getAlbums() {
        getAlbums.invoke().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data.isNullOrEmpty()) {
                        _uiState.value = HomeUIState.Nothing
                    } else {
                        _state.value = state.value.copy(album = result.data!!)
                        _uiState.value = HomeUIState.UpdateAlbum(result.data!!)
                    }
                }
                is Resource.Loading -> _uiState.value = HomeUIState.Loading(result.progressBarState)
                is Resource.Error -> _uiState.value = HomeUIState.Error(result.uiComponent)
            }
        }.launchIn(viewModelScope)
    }
}

sealed class HomeEvent {
    object GetAlbums : HomeEvent()
}

sealed class HomeUIState {
    data class UpdateAlbum(val album: List<Album> = emptyList()) : HomeUIState()
    data class Loading(val progressBarState: ProgressBarState = ProgressBarState.Idle) : HomeUIState()
    data class Error(val uiComponent: UIComponent) : HomeUIState()
    object Nothing : HomeUIState()
}

data class UIState(
    val album: List<Album> = emptyList(),
)