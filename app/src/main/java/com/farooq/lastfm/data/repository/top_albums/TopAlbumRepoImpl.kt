package com.farooq.lastfm.data.repository.top_albums

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.farooq.core.domain.NetworkExceptionHandling
import com.farooq.lastfm.data.remote.top_albums.GetTopAlbumsApi
import com.farooq.lastfm.domain.repository.TopAlbumsRepo
import javax.inject.Inject

class TopAlbumRepoImpl @Inject constructor(
    private val getTopAlbumsApi: GetTopAlbumsApi,
    private val networkExceptionHandling: NetworkExceptionHandling
) : TopAlbumsRepo {

    override fun getTopAlbums(artistName: String) = Pager(
        config = PagingConfig(NETWORK_PAGE_SIZE, maxSize = 100),
        pagingSourceFactory = { TopAlbumsPagingSource(artistName = artistName, topAlbumsApi = getTopAlbumsApi,networkExceptionHandling) }
    ).flow

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}