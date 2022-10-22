package com.farooq.lastfm.data.mapper

import com.farooq.core.utils.GsonParser
import com.farooq.lastfm.data.local.model.AlbumInfoEntity
import com.farooq.lastfm.data.remote.album_info.dto.AlbumInfoDto
import com.farooq.lastfm.data.remote.album_info.dto.ImageDto
import com.farooq.lastfm.data.remote.album_info.dto.TrackDto
import com.farooq.lastfm.data.remote.album_info.dto.TracksDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun AlbumInfoDto.toAlbumEntity(): AlbumInfoEntity {
    val album = this.album
    return AlbumInfoEntity(
        name = album.name,
        artist = album.artist,
        image = album.image.toEntityImage(),
        tracks = album.tracks?.toEntityTrack()
    )
}

fun List<ImageDto>?.toEntityImage(): List<AlbumInfoEntity.Image>? {
    return this?.map {
        AlbumInfoEntity.Image(it.text)
    }
}

fun TracksDto.toEntityTrack(): List<AlbumInfoEntity.Track> {
    if (track?.isJsonArray == true) {
        return (com.farooq.core.utils.GsonParser(Gson()).fromJson<List<TrackDto>>(track.asJsonArray.toString(), object : TypeToken<ArrayList<TrackDto>>() {}.type))?.map {
            AlbumInfoEntity.Track(
                name = it.name,
                artist = it.artist.name,
                rank = it.attr.rank,
                duration = it.duration
            )
        } ?: emptyList()
    } else if (track?.isJsonObject == true) {
        val obj = com.farooq.core.utils.GsonParser(Gson()).fromJson<TrackDto?>(track.asJsonObject.toString(), object : TypeToken<TrackDto?>() {}.type)
        obj?.let {
            return listOf(
                AlbumInfoEntity.Track(
                    name = it.name,
                    artist = it.artist.name,
                    rank = it.attr.rank,
                    duration = it.duration
                )
            )
        }
    }
    return emptyList()
}