package com.farooq.lastfm.presentation.album_info

sealed class AlbumInfoEvent {
    data class GetAlbumInfo(val artistName: String, val albumName: String) : AlbumInfoEvent()
    data class DeleteAlbumInfo(val albumName: String) : AlbumInfoEvent()
}