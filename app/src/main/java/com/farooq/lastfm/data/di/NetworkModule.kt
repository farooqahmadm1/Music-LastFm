package com.farooq.lastfm.data.di

import com.farooq.lastfm.data.remote.album_info.GetAlbumInfoApi
import com.farooq.lastfm.data.remote.top_albums.GetTopAlbumsApi
import com.farooq.lastfm.data.remote.search_artist.SearchArtistApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesSearchArtistApi(retrofit: Retrofit): SearchArtistApi {
        return retrofit.create(SearchArtistApi::class.java)
    }

    @Provides
    fun providesTopAlbumsApi(retrofit: Retrofit): GetTopAlbumsApi {
        return retrofit.create(GetTopAlbumsApi::class.java)
    }

    @Provides
    fun providesTopAlbumsInfoApi(retrofit: Retrofit): GetAlbumInfoApi {
        return retrofit.create(GetAlbumInfoApi::class.java)
    }
}