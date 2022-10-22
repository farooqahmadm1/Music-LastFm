package com.farooq.lastfm.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.farooq.lastfm.data.local.model.SearchArtist

@Dao
interface SearchArtistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<SearchArtist>)

    @Query("SELECT * FROM search_artist WHERE name LIKE :queryString")
    fun reposByName(queryString: String): PagingSource<Int, SearchArtist>

    @Query("DELETE FROM search_artist")
    suspend fun clearRepos()
}
