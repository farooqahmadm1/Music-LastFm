package com.farooq.lastfm.domain.repository

import androidx.paging.PagingData
import com.farooq.lastfm.data.remote.top_albums.dto.Album
import kotlinx.coroutines.flow.Flow

interface TopAlbumsRepo {
    fun getTopAlbums(artistName: String): Flow<PagingData<Album>>
}