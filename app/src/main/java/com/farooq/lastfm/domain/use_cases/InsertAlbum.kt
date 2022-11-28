package com.farooq.lastfm.domain.use_cases

import com.farooq.core.domain.NetworkExceptionHandling
import com.farooq.core.domain.ProgressBarState
import com.farooq.core.domain.Resource
import com.farooq.lastfm.domain.model.AlbumInfo
import com.farooq.lastfm.domain.repository.AlbumInfoRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertAlbum @Inject constructor(
    private val albumInfoRepo: AlbumInfoRepo,
    private val networkExceptionHandling: NetworkExceptionHandling
) {

    operator fun invoke(album: AlbumInfo) = flow {
        try {
            emit(Resource.Loading(ProgressBarState.Loading))
            emit(Resource.Success(albumInfoRepo.insert(album)))
        } catch (e: Exception) {
            emit(networkExceptionHandling.execute(e))
        } finally {
            emit(Resource.Loading(ProgressBarState.Idle))
        }
    }

}