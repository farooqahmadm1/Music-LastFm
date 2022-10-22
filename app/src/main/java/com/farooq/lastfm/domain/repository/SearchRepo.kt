package com.farooq.lastfm.domain.repository

import androidx.paging.PagingData
import com.farooq.lastfm.data.local.model.SearchArtist
import kotlinx.coroutines.flow.Flow


interface SearchRepo {
    fun getSearchArtist(query: String) : Flow<PagingData<SearchArtist>>
}