package com.farooq.lastfm.presentation.top_albums

sealed class TopAlbumEvent {
    data class GetTopAlbums(val artistName: String) : TopAlbumEvent()
}