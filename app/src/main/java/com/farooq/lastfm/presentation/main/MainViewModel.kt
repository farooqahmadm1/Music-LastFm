package com.farooq.lastfm.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farooq.core.domain.ProgressBarState
import com.farooq.core.domain.Resource
import com.farooq.core.domain.UIComponent
import com.farooq.lastfm.domain.model.AlbumInfo
import com.farooq.lastfm.domain.use_cases.InsertAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val insertAlbum: InsertAlbum
) : ViewModel() {

    private var _uiState: MutableStateFlow<MainUIState> = MutableStateFlow(MainUIState.Nothing)
    var uiState: StateFlow<MainUIState> = _uiState

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.InsertAlbum -> undoAlbumDelete(event.albumInfo)
        }
    }

    private fun undoAlbumDelete(albumInfo: AlbumInfo) {
        insertAlbum.invoke(albumInfo).onEach { result ->
            when (result) {
                is Resource.Success -> _uiState.value = MainUIState.RedoAlbumSuccess
                is Resource.Error -> _uiState.value = MainUIState.Error(result.uiComponent)
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

}

sealed class MainUIState {
    object RedoAlbumSuccess : MainUIState()
    data class Loading(val progressBarState: ProgressBarState = ProgressBarState.Idle) : MainUIState()
    data class Error(val uiComponent: UIComponent) : MainUIState()
    object Nothing : MainUIState()
}

sealed class MainEvent {
    data class InsertAlbum(val albumInfo: AlbumInfo) : MainEvent()
}