package com.farooq.lastfm.data.repository.top_albums

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.farooq.core.domain.NetworkExceptionHandling
import com.farooq.lastfm.data.remote.top_albums.GetTopAlbumsApi
import com.farooq.lastfm.data.remote.top_albums.dto.Album

class TopAlbumsPagingSource(
    private val artistName: String,
    private val topAlbumsApi: GetTopAlbumsApi,
    private val networkExceptionHandling: NetworkExceptionHandling
) : PagingSource<Int, Album>() {

    override fun getRefreshKey(state: PagingState<Int, Album>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {
        return try {
            val position = params.key ?: 1
            val response = topAlbumsApi.getTopAlbumByArtist(artistName, position)
            LoadResult.Page(
                data = response.topAlbums.album,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (response.topAlbums.attr.page == response.topAlbums.attr.totalPages) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(Exception(networkExceptionHandling.execute(e)))
        }
    }

}