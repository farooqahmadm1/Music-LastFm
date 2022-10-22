package com.farooq.lastfm.data.remote.album_info.dto


import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class AlbumInfoDto(
    @SerializedName("album")
    val album: AlbumDto
)

data class AlbumDto(
    @SerializedName("artist")
    val artist: String,
    @SerializedName("image")
    val image: List<ImageDto>?,
    @SerializedName("listeners")
    val listeners: String,
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("playcount")
    val playcount: String,
    @SerializedName("tags")
    val tags: Any?,
    @SerializedName("tracks")
    val tracks: TracksDto?,
    @SerializedName("url")
    val url: String
)

data class ImageDto(
    @SerializedName("size")
    val size: String,
    @SerializedName("#text")
    val text: String
)

data class Tags(
    @SerializedName("tag")
    val tag: List<Tag>?
)

data class Tag(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)


data class TracksDto(
    @SerializedName("track")
    val track: JsonElement?
)

data class TrackDto(
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("@attr")
    val attr: Attr,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("streamable")
    val streamable: Streamable,
    @SerializedName("url")
    val url: String
)

data class Artist(
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class Attr(
    @SerializedName("rank")
    val rank: Int
)

data class Streamable(
    @SerializedName("fulltrack")
    val fulltrack: String,
    @SerializedName("#text")
    val text: String
)