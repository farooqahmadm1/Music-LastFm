package com.farooq.lastfm.domain.use_cases

import com.farooq.lastfm.domain.repository.TopAlbumsRepo
import javax.inject.Inject

class GetTopAlbums @Inject constructor(
    private val topAlbumsRepo: TopAlbumsRepo
) {
    operator fun invoke(artistName: String) = topAlbumsRepo.getTopAlbums(artistName)
}