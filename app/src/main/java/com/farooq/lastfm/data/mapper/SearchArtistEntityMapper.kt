package com.farooq.lastfm.data.mapper

import com.farooq.lastfm.data.local.model.SearchArtist
import com.farooq.lastfm.data.remote.search_artist.dto.ArtistDto
import com.farooq.lastfm.data.remote.search_artist.dto.ArtistImageDto


fun ArtistDto.toSearchArtistEntity(): SearchArtist {
    return SearchArtist(
        this.name,
        this.mbid,
        this.listeners,
        this.image.toSearchArtistImage()
    )
}

fun List<ArtistImageDto>?.toSearchArtistImage(): List<SearchArtist.Image>? {
    return this?.map {
        SearchArtist.Image(it.text)
    }
}