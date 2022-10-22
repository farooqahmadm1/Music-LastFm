package com.farooq.lastfm.data.repository.search_artist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.farooq.lastfm.data.remote.search_artist.SearchArtistApi
import com.farooq.lastfm.data.remote.search_artist.dto.ArtistDto

class SearchPagingSource(
    private val query: String,
    private val search: SearchArtistApi
) : PagingSource<Int, ArtistDto>() {

    override fun getRefreshKey(state: PagingState<Int, ArtistDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtistDto> {
        return try {
            val position = params.key ?: 1
            val response = search.getSearchArtists(query,position,30)
            val artists = response.results?.artistMatches?.artist
            LoadResult.Page(
                data = artists!!,
                prevKey = if (position == 1) null else position-1,
                nextKey = if (response.results.openSearchStartIndex.toInt() > response.results.openSearchTotalResults.toInt()) null else position+1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}