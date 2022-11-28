package com.farooq.lastfm.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album_info_table")
data class AlbumInfoEntity(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val artist: String,
    val image: List<Image>?,
    val tracks: List<Track>?,
) {

    data class Image(
        val text: String?
    )

    data class Track(
        val artist: String,
        val rank: Int,
        val duration: Int,
        val name: String,
    )
}