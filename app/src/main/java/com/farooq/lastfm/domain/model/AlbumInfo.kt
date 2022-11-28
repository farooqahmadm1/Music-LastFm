package com.farooq.lastfm.domain.model

data class AlbumInfo(
    val artist: String,
    val name: String,
    val image: String?,
    val tracks: List<Track>?,
) {

    data class Image(
        val text: String
    )

    data class Track(
        val artist: String,
        val rank: Int,
        val duration: Int,
        val name: String,
    )
}