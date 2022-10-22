package com.farooq.lastfm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.farooq.lastfm.data.local.dao.AlbumInfoDao
import com.farooq.lastfm.data.local.dao.AlbumRemoteKeysDao
import com.farooq.lastfm.data.local.dao.SearchArtistDao
import com.farooq.lastfm.data.local.dao.SearchRemoteKeysDao
import com.farooq.lastfm.data.local.model.AlbumInfoEntity
import com.farooq.lastfm.data.local.model.AlbumRemoteKeys
import com.farooq.lastfm.data.local.model.SearchArtist
import com.farooq.lastfm.data.local.model.SearchRemoteKeys

@Database(entities = [
    AlbumRemoteKeys::class,
    AlbumInfoEntity::class,
    SearchRemoteKeys::class,
    SearchArtist::class
], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun albumRemoteKeysDao(): AlbumRemoteKeysDao
    abstract fun albumInfoDao(): AlbumInfoDao
    abstract fun searchRemoteKeysDao(): SearchRemoteKeysDao
    abstract fun searchArtistDao(): SearchArtistDao
}