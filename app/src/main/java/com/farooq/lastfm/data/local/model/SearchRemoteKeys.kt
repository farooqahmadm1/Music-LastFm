package com.farooq.lastfm.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_remote_keys")
data class SearchRemoteKeys(
    @PrimaryKey val artist_name: String,
    val prevKey: Int?,
    val nextKey: Int?
)
