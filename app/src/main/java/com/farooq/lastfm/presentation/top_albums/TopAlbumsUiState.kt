package com.farooq.lastfm.presentation.top_albums

import androidx.paging.PagingData
import com.farooq.lastfm.data.remote.top_albums.dto.Album

data class TopAlbumsUiState(
    val albums : PagingData<Album>? = null
)