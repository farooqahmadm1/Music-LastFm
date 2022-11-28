package com.farooq.lastfm.domain.repository

import com.farooq.lastfm.domain.model.AlbumInfo
import kotlinx.coroutines.flow.Flow


interface AlbumInfoRepo {
    fun getAlbumInfo(artistName: String, albumName: String): Flow<AlbumInfo?>
    suspend fun delete(albumName: String): Int
    suspend fun insert(albumInfo: AlbumInfo) : Long
}