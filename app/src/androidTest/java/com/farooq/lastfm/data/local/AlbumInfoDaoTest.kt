package com.farooq.lastfm.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
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

    @Inject lateinit var dao: AlbumInfoDao
    @Inject lateinit var db: AppDatabase


    @Before
    fun setUp() {
        rule.inject()
    }

    @Test
    fun insertAlbumInfo() = runBlocking {
        val albumInfo = AlbumInfoEntity("test", "artist", emptyList(), emptyList())
        dao.insert(albumInfo)
        val result = dao.getAlbumInfo("test")
        assertThat(albumInfo).isEqualTo(result)
    }

    @Test
    fun getAlbumInfo() = runBlocking {
        val albumInfo = AlbumInfoEntity("test", "artist", emptyList(), emptyList())
        dao.insert(albumInfo)
        val result = dao.getAlbumInfo("test")
        assertThat(albumInfo).isEqualTo(result)
    }

    @Test
    fun getAlbumInfoList() = runBlocking {
        var list :List<AlbumInfoEntity> = emptyList()
        dao.insert(AlbumInfoEntity("test1", "artist", emptyList(), emptyList()))
        dao.insert(AlbumInfoEntity("test2", "artist", emptyList(), emptyList()))
        val job = launch {
            dao.getAlbums().collectLatest {
                list = it
            }
        }
        delay(1000)
        job.cancel()
        assertThat(list.size).isEqualTo(2)
    }

    @Test
    fun deleteAlbumInfoList() = runBlocking {
        val albumInfo = AlbumInfoEntity("test", "artist", emptyList(), emptyList())
        dao.insert(albumInfo)
        val result = dao.delete("test")
        assertThat(result).isEqualTo(1)
    }

    @After
    fun tearDown() {
        db.close()
    }
}