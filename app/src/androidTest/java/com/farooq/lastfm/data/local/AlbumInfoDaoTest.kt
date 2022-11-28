package com.farooq.lastfm.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.farooq.lastfm.data.local.dao.AlbumInfoDao
import com.farooq.lastfm.data.local.model.AlbumInfoEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class AlbumInfoDaoTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Inject
    lateinit var converters: Converters

    @Inject
    lateinit var dao: AlbumInfoDao

    @Inject
    lateinit var db: AppDatabase

    @Before
    fun setUp() {
        rule.inject()
    }

    @Test
    fun success_insert_album_info() = runBlocking {
        val albumInfo = AlbumInfoEntity("test", "artist", emptyList(), emptyList())
        dao.insert(albumInfo)
        val result = dao.getAlbumInfo("test")
        assertThat(albumInfo).isEqualTo(result)
    }

    @Test
    fun success_get_album_info() = runBlocking {
        val albumInfo = AlbumInfoEntity("test", "artist", emptyList(), emptyList())
        dao.insert(albumInfo)
        val result = dao.getAlbumInfo("test")
        assertThat(albumInfo).isEqualTo(result)
    }

    @Test
    fun success_get_album_info_list() = runBlocking {
        var list: List<AlbumInfoEntity> = emptyList()
        dao.insert(AlbumInfoEntity("test1", "artist", emptyList(), emptyList()))
        dao.insert(AlbumInfoEntity("test2", "artist", emptyList(), emptyList()))
        list = dao.getAlbums().first()
        assertThat(list.size).isEqualTo(2)
    }

    @Test
    fun success_delete_album_info_item() = runBlocking {
        val albumInfo = AlbumInfoEntity("test", "artist", emptyList(), emptyList())
        dao.insert(albumInfo)
        val result = dao.delete("test")
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun error_delete_album_info_item() = runBlocking {
        val result = dao.delete("testfs")
        assertThat(result).isEqualTo(0)
    }

    @After
    fun tearDown() {
        db.close()
    }
}