package com.farooq.lastfm.domain.use_cases

import com.farooq.core.domain.*
import com.farooq.lastfm.domain.repository.AlbumInfoRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteAlbumInfo @Inject constructor(
    private val repo: AlbumInfoRepo,
    private val exceptionHandling: NetworkExceptionHandling
) {

    operator fun invoke(albumName: String) = flow {
        try {
            emit(Resource.Loading(ProgressBarState.Loading))
            val result = repo.delete(albumName)
            if (result == 1) {
                emit(Resource.Success(result))
            } else {
                emit(Resource.Error(UIComponent.None("Something went wrong"), ErrorType.UNKNOWN))
            }
        } catch (e: Exception) {
            emit(exceptionHandling.execute(e))
        } finally {
            emit(Resource.Loading(ProgressBarState.Idle))
        }
    }
}