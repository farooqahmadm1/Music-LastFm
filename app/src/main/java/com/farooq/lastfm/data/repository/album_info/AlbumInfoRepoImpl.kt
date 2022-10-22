package com.farooq.lastfm.data.repository.album_info

import com.farooq.lastfm.data.local.dao.AlbumInfoDao
import com.farooq.lastfm.data.mapper.toAlbumEntity
import com.farooq.lastfm.data.mapper.toDomainAlbumInfo
import com.farooq.lastfm.data.remote.album_info.GetAlbumInfoApi
import com.farooq.lastfm.domain.repository.AlbumInfoRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlbumInfoRepoImpl @Inject constructor(
    private val albumInfoApi: GetAlbumInfoApi,
    private val dao: AlbumInfoDao,
) : AlbumInfoRepo {

    override fun getAlbumInfo(artistName: String, albumName: String) = flow {
        val cacheAlbum = dao.getAlbumInfo(albumName)?.toDomainAlbumInfo()
        cacheAlbum?.let {
            emit(it)
        } ?: kotlin.run {
            val albumInfo = albumInfoApi.getAlbumInfo(artist = artistName, album = albumName)
            emit(dao.albumInfo(albumName, albumInfo.toAlbumEntity())?.toDomainAlbumInfo())
        }
    }

    override suspend fun delete(albumName: String) = dao.delete(albumName)

}