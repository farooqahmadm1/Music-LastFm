package com.farooq.lastfm.data.remote.top_albums.dto


import com.google.gson.annotations.SerializedName

data class TopAlbumsDto(
    @SerializedName("topalbums")
    val topAlbums: TopAlbums
)

data class TopAlbums(
    @SerializedName("album")
    val album: List<Album>,
    @SerializedName("@attr")
    val attr: Attr
)

data class Album(
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("image")
    val image: List<Image>,
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("playcount")
    val playCount: Int,
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

data class Image(
    @SerializedName("size")
    val size: String,
    @SerializedName("#text")
    val text: String
)

data class Attr(
    @SerializedName("artist")
    val artist: String,
    @SerializedName("page")
    val page: String,
    @SerializedName("perPage")
    val perPage: String,
    @SerializedName("total")
    val total: String,
    @SerializedName("totalPages")
    val totalPages: String
)