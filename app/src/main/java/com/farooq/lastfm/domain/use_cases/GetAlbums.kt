package com.farooq.lastfm.domain.use_cases

import com.farooq.core.domain.NetworkExceptionHandling
import com.farooq.core.domain.ProgressBarState
import com.farooq.core.domain.Resource
import com.farooq.lastfm.domain.repository.GetAlbumsRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class GetAlbums @Inject constructor(
    private val getAlbumsRepo: GetAlbumsRepo,
    private val networkExceptionHandling: NetworkExceptionHandling
) {

    operator fun invoke() = flow {
        try {
            emit(Resource.Loading(ProgressBarState.Loading))
            emit(Resource.Success(getAlbumsRepo.getAlbumsRepo().first()))
        } catch (e: Exception) {
            emit(networkExceptionHandling.execute(e))
        } finally {
            emit(Resource.Loading(ProgressBarState.Idle))
        }
    }

}