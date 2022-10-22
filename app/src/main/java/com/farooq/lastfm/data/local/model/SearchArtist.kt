package com.farooq.lastfm.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "search_artist")
data class SearchArtist(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val mbid: String,
    val listeners: String?,
    val image: List<Image>?
) {

    data class Image(
        val text: String
    )
}

