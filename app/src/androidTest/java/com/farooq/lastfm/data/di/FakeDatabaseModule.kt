package com.farooq.lastfm.data.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.farooq.lastfm.data.local.AppDatabase
import com.farooq.lastfm.data.local.Converters
import com.farooq.lastfm.data.local.dao.AlbumInfoDao
import com.farooq.lastfm.data.local.dao.SearchArtistDao
import com.farooq.lastfm.data.local.dao.SearchRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
class FakeDatabaseModule {

    @Provides
    fun providesRoomDb(context: Context, converters: Converters): AppDatabase {
        val context = ApplicationProvider.getApplicationContext<Context>()
        return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
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