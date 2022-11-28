package com.farooq.lastfm.domain.repository

import com.farooq.lastfm.data.local.dao.AlbumInfoDao
import com.farooq.lastfm.data.local.model.AlbumInfoEntity
import com.farooq.lastfm.domain.model.Album
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@HiltAndroidTest
@RunWith(JUnit4::class)
class GetAlbumsRepoTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Inject
    lateinit var repo: GetAlbumsRepo

    @Inject
    lateinit var dao: AlbumInfoDao

    @Before
    fun setUp() {
        rule.inject()
    }

    @Test
    fun success_result_with_empty_list() = runBlocking {
        val result = dao.getAlbums().first()
        Truth.assertThat(result.size).isEqualTo(0)
    }

    @Test
    fun success_result_with_1_obj() = runBlocking {
        var list: List<Album>? = null
        dao.insert(AlbumInfoEntity("test1", "artist", emptyList(), emptyList()))
        list = repo.getAlbumsRepo().first()
        Truth.assertThat(list.size).isEqualTo(1)
    }

}