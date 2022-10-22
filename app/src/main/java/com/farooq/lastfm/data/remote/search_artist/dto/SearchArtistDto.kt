package com.farooq.lastfm.data.remote.search_artist.dto


import com.google.gson.annotations.SerializedName

data class SearchArtistDto(
    @SerializedName("results")
    val results: Results?
)

data class Results(
    @SerializedName("artistmatches")
    val artistMatches: ArtistMatches?,
    @SerializedName("@attr")
    val attr: Attr,
    @SerializedName("opensearch:itemsPerPage")
    val openSearchItemsPerPage: String,
    @SerializedName("opensearch:Query")
    val openSearchQuery: OpenSearchQuery,
    @SerializedName("opensearch:startIndex")
    val openSearchStartIndex: String,
    @SerializedName("opensearch:totalResults")
    val openSearchTotalResults: String
)

data class ArtistMatches(
    @SerializedName("artist")
    val artist: List<ArtistDto>
)

data class ArtistDto(
    @SerializedName("image")
    val image: List<ArtistImageDto>,
    @SerializedName("listeners")
    val listeners: String,
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("streamable")
    val streamable: String,
    @SerializedName("url")
    val url: String
)

data class ArtistImageDto(
    @SerializedName("size")
    val size: String,
    @SerializedName("#text")
    val text: String
)

data class Attr(
    @SerializedName("for")
    val forX: String
)

data class OpenSearchQuery(
    @SerializedName("role")
    val role: String,
    @SerializedName("searchTerms")
    val searchTerms: String,
    @SerializedName("startPage")
    val startPage: String,
    @SerializedName("#text")
    val text: String
)