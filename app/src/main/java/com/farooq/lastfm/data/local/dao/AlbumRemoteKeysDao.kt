package com.farooq.lastfm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.farooq.lastfm.data.local.model.AlbumRemoteKeys


@Dao
interface AlbumRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<AlbumRemoteKeys>)

    @Query("SELECT * FROM album_remote_keys WHERE albumId = :albumId")
    suspend fun remoteKeysRepoId(albumId: String): AlbumRemoteKeys?

    @Query("DELETE FROM album_remote_keys")
    suspend fun clearRemoteKeys()

}
