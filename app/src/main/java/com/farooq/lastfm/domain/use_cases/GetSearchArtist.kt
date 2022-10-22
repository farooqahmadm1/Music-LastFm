package com.farooq.lastfm.domain.use_cases

import com.farooq.lastfm.domain.repository.SearchRepo
import javax.inject.Inject

class GetSearchArtist @Inject constructor(
    private val repo: SearchRepo
) {
    operator fun invoke(query: String) = repo.getSearchArtist(query)
}