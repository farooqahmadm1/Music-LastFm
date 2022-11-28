package com.farooq.lastfm.data.mapper

import com.farooq.lastfm.data.local.model.AlbumInfoEntity
import com.farooq.lastfm.domain.model.AlbumInfo


fun AlbumInfoEntity.toDomainAlbumInfo(): AlbumInfo {
    return AlbumInfo(
        name = this.name,
        artist = this.artist,
        image = this.image.toDomainImageUrl(),
        tracks = this.tracks.toDomainTrack()
    )
}

fun List<AlbumInfoEntity.Image>?.toDomainImage(): List<AlbumInfo.Image>? {
    return this?.map {
        AlbumInfo.Image(it.text ?: "")
    }
}

fun List<AlbumInfoEntity.Image>?.toDomainImageUrl(): String? {
    return this?.getOrNull(0)?.text
}

fun List<AlbumInfoEntity.Track>?.toDomainTrack(): List<AlbumInfo.Track>? {
    return this?.map {
        AlbumInfo.Track(
            name = it.name,
            artist = it.artist,
            rank = it.rank,
            duration = it.duration
        )
    }
}

