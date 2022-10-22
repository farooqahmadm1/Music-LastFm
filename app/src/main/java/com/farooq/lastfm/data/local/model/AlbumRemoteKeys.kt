package com.farooq.lastfm.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "album_remote_keys")
data class AlbumRemoteKeys(
    @PrimaryKey val albumId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
