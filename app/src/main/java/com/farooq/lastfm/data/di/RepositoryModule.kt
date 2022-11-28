package com.farooq.lastfm.data.di

import com.farooq.lastfm.data.repository.album_info.AlbumInfoRepoImpl
import com.farooq.lastfm.data.repository.albums.GetAlbumsRepoImpl
import com.farooq.lastfm.data.repository.search_artist.SearchRepoImpl
import com.farooq.lastfm.data.repository.top_albums.TopAlbumRepoImpl
import com.farooq.lastfm.domain.repository.AlbumInfoRepo
import com.farooq.lastfm.domain.repository.GetAlbumsRepo
import com.farooq.lastfm.domain.repository.SearchRepo
import com.farooq.lastfm.domain.repository.TopAlbumsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepo(searchRepoImpl: SearchRepoImpl): SearchRepo

    @Binds
    @Singleton
    abstract fun bindTopAlbumRepo(topAlbumRepoImpl: TopAlbumRepoImpl): TopAlbumsRepo

    @Binds
    @Singleton
    abstract fun bindAlbumInfoRepo(topAlbumRepoImpl: AlbumInfoRepoImpl): AlbumInfoRepo

    @Binds
    @Singleton
    abstract fun bindGetAlbumsRepo(getAlbumsRepo: GetAlbumsRepoImpl): GetAlbumsRepo
}