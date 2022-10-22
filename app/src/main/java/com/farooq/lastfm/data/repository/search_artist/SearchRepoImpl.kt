package com.farooq.lastfm.data.repository.search_artist

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.farooq.core.domain.NetworkExceptionHandling
import com.farooq.lastfm.data.local.AppDatabase
import com.farooq.lastfm.data.local.model.SearchArtist
import com.farooq.lastfm.data.remote.search_artist.SearchArtistApi
import com.farooq.lastfm.domain.repository.SearchRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val searchArtistApi: SearchArtistApi,
    private val database: AppDatabase,
    private val networkExceptionHandling: NetworkExceptionHandling
) : SearchRepo {

    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    override fun getSearchArtist(query: String): Flow<PagingData<SearchArtist>> {
        Log.d("GithubRepository", "New query: $query")

        // appending '%' so we can allow other characters to be before and after the query string
        val dbQuery = "%${query}%"
        val pagingSourceFactory = { database.searchArtistDao().reposByName(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = SearchPagingRemoteMediater(
                query,
                searchArtistApi,
                database,
                networkExceptionHandling
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }

}