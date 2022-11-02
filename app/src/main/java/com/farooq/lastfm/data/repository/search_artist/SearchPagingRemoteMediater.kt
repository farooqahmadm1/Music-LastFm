package com.farooq.lastfm.data.repository.search_artist

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.farooq.core.domain.NetworkExceptionHandling
import com.farooq.lastfm.data.local.AppDatabase
import com.farooq.lastfm.data.local.model.SearchArtist
import com.farooq.lastfm.data.local.model.SearchRemoteKeys
import com.farooq.lastfm.data.mapper.toSearchArtistEntity
import com.farooq.lastfm.data.remote.search_artist.SearchArtistApi
import retrofit2.HttpException
import java.io.IOException

private const val SEARCH_ARTIST_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class SearchPagingRemoteMediater(
    private val query: String,
    private val service: SearchArtistApi,
    private val appDatabase: AppDatabase,
    private val networkExceptionHandling: NetworkExceptionHandling
) : RemoteMediator<Int, SearchArtist>() {

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, SearchArtist>): RemoteMediator.MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: SEARCH_ARTIST_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        val apiQuery = query

        try {
            val apiResponse = service.getSearchArtists(apiQuery, page, state.config.pageSize)

            val searchArtists = apiResponse.results?.artistMatches?.artist
            val endOfPaginationReached = searchArtists.isNullOrEmpty()
            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDatabase.searchRemoteKeysDao().clearRemoteKeys()
                    appDatabase.searchArtistDao().clearRepos()
                }
                val prevKey = if (page == SEARCH_ARTIST_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = searchArtists?.map {
                    SearchRemoteKeys(artist_name = it.name, prevKey = prevKey, nextKey = nextKey)
                }
                if (keys != null) {
                    appDatabase.searchRemoteKeysDao().insertAll(keys)
                }
                searchArtists?.map { it.toSearchArtistEntity() }?.let {
                    appDatabase.searchArtistDao().insertAll(it)
                }
            }
            return RemoteMediator.MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: HttpException) {
            return RemoteMediator.MediatorResult.Error(Exception(networkExceptionHandling.handleMessage(exception)))
        } catch (exception: IOException) {
            return RemoteMediator.MediatorResult.Error(exception)
        } catch (exception: Exception) {
            return RemoteMediator.MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, SearchArtist>): SearchRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { artist ->
            // Get the remote keys of the last item retrieved
            appDatabase.searchRemoteKeysDao().remoteKeysRepoId(artist.name)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, SearchArtist>): SearchRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { repo ->
            // Get the remote keys of the first items retrieved
            appDatabase.searchRemoteKeysDao().remoteKeysRepoId(repo.name)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, SearchArtist>): SearchRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { repoId ->
                appDatabase.searchRemoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }
}