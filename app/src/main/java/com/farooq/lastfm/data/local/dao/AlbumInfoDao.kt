package com.farooq.lastfm.data.local.dao

import androidx.room.*
import com.farooq.lastfm.data.local.model.AlbumInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(albumInfoEntity: AlbumInfoEntity) : Long

    @Query("SELECT * FROM album_info_table WHERE name = :albumName")
    suspend fun getAlbumInfo(albumName: String): AlbumInfoEntity?

    @Query("DELETE FROM album_info_table WHERE name = :albumName")
    suspend fun delete(albumName: String) : Int

    @Query("SELECT * FROM album_info_table")
    fun getAlbums(): Flow<List<AlbumInfoEntity>>

    @Transaction
    suspend fun albumInfo(albumName: String,albumInfoEntity: AlbumInfoEntity?) : AlbumInfoEntity?{
        if (albumInfoEntity != null) {
            insert(albumInfoEntity)
        }
        return getAlbumInfo(albumName)
    }
}