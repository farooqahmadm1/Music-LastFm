package com.farooq.lastfm.presentation.album_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farooq.core.domain.ProgressBarState
import com.farooq.core.domain.Resource
import com.farooq.core.domain.UIComponent
import com.farooq.lastfm.domain.interactor.AlbumInfoInteractor
import com.farooq.lastfm.domain.model.AlbumInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AlbumInfoViewModel @Inject constructor(
    private val interactor: AlbumInfoInteractor
) : ViewModel() {

    private var _uiState: MutableStateFlow<AlbumUIState> = MutableStateFlow(AlbumUIState.Nothing)
    var uiState: StateFlow<AlbumUIState> = _uiState

    private var _dateState: MutableStateFlow<AlbumDataState> = MutableStateFlow(AlbumDataState())
    var dataState: StateFlow<AlbumDataState> = _dateState

    fun onEvent(event: AlbumInfoEvent) {
        when (event) {
            is AlbumInfoEvent.GetAlbumInfo -> getAlbumInfo(artistName = event.artistName, albumName = event.albumName)
            is AlbumInfoEvent.DeleteAlbumInfo -> deleteAlbumInfo(albumName = event.albumName)
        }
    }

    private fun getAlbumInfo(artistName: String, albumName: String) {
        interactor.getAlbumInfo.invoke(artistName, albumName).onEach { result ->
            when (result) {
                is Resource.Loading -> _uiState.value = AlbumUIState.Loading(result.progressBarState)
                is Resource.Error -> _uiState.value = AlbumUIState.Error(result.uiComponent)
                is Resource.Success -> {
                    result.data?.let {
                        _dateState.value = dataState.value.copy(album = it)
                        _uiState.value = AlbumUIState.UpdateAlbum(it)
                    } ?: kotlin.run {
                        _uiState.value = AlbumUIState.Nothing
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteAlbumInfo(albumName: String) {
        interactor.deleteAlbumInfo.invoke(albumName).onEach { result ->
            when (result) {
                is Resource.Loading -> _uiState.value = AlbumUIState.Loading(result.progressBarState)
                is Resource.Error -> _uiState.value = AlbumUIState.Error(result.uiComponent)
                is Resource.Success -> _uiState.value = AlbumUIState.DeleteAlbumSuccess
            }
        }.launchIn(viewModelScope)
    }

}

sealed class AlbumUIState {
    data class UpdateAlbum(val album: AlbumInfo) : AlbumUIState()
    object DeleteAlbumSuccess : AlbumUIState()
    data class Loading(val progressBarState: ProgressBarState = ProgressBarState.Idle) : AlbumUIState()
    data class Error(val uiComponent: UIComponent) : AlbumUIState()
    object Nothing : AlbumUIState()
}

data class AlbumDataState(
    val album: AlbumInfo? = null
)