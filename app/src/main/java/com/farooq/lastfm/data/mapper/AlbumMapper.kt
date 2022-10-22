package com.farooq.lastfm.data.mapper

import com.farooq.lastfm.data.local.model.AlbumInfoEntity
import com.farooq.lastfm.domain.model.Album

fun AlbumInfoEntity.toDomainAlbum(): Album {
    return Album(
        name = this.name,
        artist = this.artist,
        image = if (this.image.isNullOrEmpty()) "" else this.image[0].text,
    )
}
