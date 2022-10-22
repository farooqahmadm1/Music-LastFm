package com.farooq.lastfm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.farooq.lastfm.data.local.model.SearchRemoteKeys

@Dao
interface SearchRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<SearchRemoteKeys>)

    @Query("SELECT * FROM search_remote_keys WHERE artist_name = :artistName")
    suspend fun remoteKeysRepoId(artistName: String): SearchRemoteKeys?

    @Query("DELETE FROM search_remote_keys")
    suspend fun clearRemoteKeys()
}
