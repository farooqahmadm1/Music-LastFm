package com.farooq.lastfm.domain.interactor

import com.farooq.lastfm.domain.use_cases.DeleteAlbumInfo
import com.farooq.lastfm.domain.use_cases.GetAlbumInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class InteractorModule {

    @Provides
    fun provideAlbumInfoInteractor(getAlbumInfo: GetAlbumInfo, deleteAlbumInfo: DeleteAlbumInfo): AlbumInfoInteractor {
        return AlbumInfoInteractor(getAlbumInfo, deleteAlbumInfo)
    }
}