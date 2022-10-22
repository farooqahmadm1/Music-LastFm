package com.farooq.lastfm.data.repository.albums

import com.farooq.lastfm.data.local.dao.AlbumInfoDao
import com.farooq.lastfm.data.mapper.toDomainAlbum
import com.farooq.lastfm.data.mapper.toDomainAlbumInfo
import com.farooq.lastfm.domain.model.Album
import com.farooq.lastfm.domain.model.AlbumInfo
import com.farooq.lastfm.domain.repository.GetAlbumsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAlbumsRepoImpl @Inject constructor(
    private val dao: AlbumInfoDao
) : GetAlbumsRepo {

    override fun getAlbumsRepo(): Flow<List<Album>> {
        return dao.getAlbums().map { list ->
            list.map { it.toDomainAlbum() }
        }
    }

}