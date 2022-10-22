package com.farooq.lastfm.data.remote.search_artist

import com.farooq.lastfm.data.remote.search_artist.dto.SearchArtistDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchArtistApi {

    @GET("/2.0/?method=artist.search")
    suspend fun getSearchArtists(
        @Query("artist") artist: String,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
    ) : SearchArtistDto
}