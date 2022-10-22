package com.farooq.lastfm.domain.interactor

import com.farooq.lastfm.domain.use_cases.DeleteAlbumInfo
import com.farooq.lastfm.domain.use_cases.GetAlbumInfo

data class AlbumInfoInteractor(
    val getAlbumInfo: GetAlbumInfo,
    val deleteAlbumInfo: DeleteAlbumInfo
)