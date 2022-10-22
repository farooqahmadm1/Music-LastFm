package com.farooq.lastfm.data.di

import android.content.Context
import androidx.room.Room
import com.farooq.lastfm.data.local.AppDatabase
import com.farooq.lastfm.data.local.Converters
import com.farooq.lastfm.data.local.dao.AlbumInfoDao
import com.farooq.lastfm.data.local.dao.SearchArtistDao
import com.farooq.lastfm.data.local.dao.SearchRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providesRoomDb(context: Context,converters: Converters): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "lastfm.db")
            .fallbackToDestructiveMigration()
            .addTypeConverter(converters)
            .build()
    }


    @Provides
    fun provideAlbumInfoDao(db: AppDatabase): AlbumInfoDao {
        return db.albumInfoDao()
    }
    @Provides
    fun provideRemoteDao(db: AppDatabase): SearchRemoteKeysDao {
        return db.searchRemoteKeysDao()
    }
    @Provides
    fun provideSearchArtistDao(db: AppDatabase): SearchArtistDao {
        return db.searchArtistDao()
    }

}