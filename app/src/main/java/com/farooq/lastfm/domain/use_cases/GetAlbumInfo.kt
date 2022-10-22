package com.farooq.lastfm.domain.use_cases

import com.farooq.core.domain.NetworkExceptionHandling
import com.farooq.core.domain.ProgressBarState
import com.farooq.core.domain.Resource
import com.farooq.lastfm.domain.repository.AlbumInfoRepo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetAlbumInfo @Inject constructor(
    private val repo: AlbumInfoRepo,
    private val networkExceptionHandling: NetworkExceptionHandling
) {

    operator fun invoke(artistName: String, albumName: String) = flow {
        try {
            emit(Resource.Loading(ProgressBarState.Loading))
            val result = repo.getAlbumInfo(artistName, albumName).first()
            emit(Resource.Success(result!!))
        } catch (e: Exception) {
            emit(networkExceptionHandling.execute(e))
        } finally {
            emit(Resource.Loading(ProgressBarState.Idle))
        }
    }
}