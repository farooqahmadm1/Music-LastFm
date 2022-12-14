package com.farooq.lastfm.domain.repository

import com.farooq.lastfm.data.local.model.AlbumInfoEntity
import com.farooq.lastfm.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface GetAlbumsRepo {
    fun getAlbumsRepo() : Flow<List<AlbumInfoEntity>>
}