package com.farooq.lastfm.data.remote.top_albums

import com.farooq.lastfm.data.remote.search_artist.dto.SearchArtistDto
import com.farooq.lastfm.data.remote.top_albums.dto.TopAlbumsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GetTopAlbumsApi {

    @GET("/2.0/?method=artist.gettopalbums")
    suspend fun getTopAlbumByArtist(
        @Query("artist") artist: String,
        @Query("page") page: Int?,
    ): TopAlbumsDto

}