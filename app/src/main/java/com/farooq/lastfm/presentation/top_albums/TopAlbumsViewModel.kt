package com.farooq.lastfm.presentation.top_albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.farooq.lastfm.domain.use_cases.GetTopAlbums
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TopAlbumsViewModel @Inject constructor(
    private val getTopAlbums: GetTopAlbums
) : ViewModel() {

    private var _dataState = MutableStateFlow(TopAlbumsUiState())
    var dataState: StateFlow<TopAlbumsUiState> = _dataState

    fun onEvent(event: TopAlbumEvent) {
        when (event) {
            is TopAlbumEvent.GetTopAlbums -> getTopAlums(event.artistName)
        }
    }

    private fun getTopAlums(name: String) {
        getTopAlbums.invoke(name).cachedIn(viewModelScope).onEach { result ->
            _dataState.value = dataState.value.copy(albums = result)
        }.launchIn(viewModelScope)
    }
}