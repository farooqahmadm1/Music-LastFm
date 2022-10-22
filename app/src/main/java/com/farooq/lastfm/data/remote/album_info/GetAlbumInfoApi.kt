package com.farooq.lastfm.data.remote.album_info

import com.farooq.lastfm.data.remote.album_info.dto.AlbumInfoDto
import com.farooq.lastfm.data.remote.top_albums.dto.TopAlbumsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GetAlbumInfoApi {

    @GET("/2.0/?method=album.getinfo")
    suspend fun getAlbumInfo(
        @Query("artist") artist: String,
        @Query("album") album: String,
    ): AlbumInfoDto

}