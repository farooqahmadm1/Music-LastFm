package com.farooq.lastfm.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farooq.core.domain.ProgressBarState
import com.farooq.core.domain.Resource
import com.farooq.core.domain.UIComponent
import com.farooq.lastfm.domain.model.Album
import com.farooq.lastfm.domain.use_cases.GetAlbums
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAlbums: GetAlbums
) : ViewModel() {

    private val _state = MutableStateFlow(UIState())
    var state: StateFlow<UIState> = _state

    private val _uiState: StateFlow<HomeUIState> = getAlbums.invoke().mapLatest {
        HomeUIState.UpdateAlbum(it)
    }.stateIn(viewModelScope, initialValue = HomeUIState.Nothing, started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 1000))

    var uiState: StateFlow<HomeUIState> = _uiState

    init {
        getAlbums()
    }

//    fun onEvent(event: HomeEvent) {
//        when (event) {
//            is HomeEvent.GetAlbums -> getAlbums()
//        }
//    }
//
//    private fun getAlbums() {
//        getAlbums.invoke().onEach { result ->
//            _state.value = state.value.copy(album = result)
//            _uiState.value = HomeUIState.UpdateAlbum(result)
//        }.launchIn(viewModelScope)
//    }
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

/*Cache the data*/
data class UIState(
    val album: List<Album> = emptyList(),
)