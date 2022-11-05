package com.farooq.lastfm.data.remote.album_info

import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@HiltAndroidTest
@RunWith(JUnit4::class)
class AlbumInfoApiTest {


    @get:Rule
    val rule = HiltAndroidRule(this)

    @Inject
    lateinit var albumInfoApi: GetAlbumInfoApi

    @Before
    fun setup() {
        rule.inject()
    }

    @Test
    fun successResultFromAlbumInfoApi() = runBlocking{
        val result  = albumInfoApi.getAlbumInfo("Cherry Vanilla","Bad Girl")
        Truth.assertThat("Bad Girl").isEqualTo(result.album.name)
    }

    @After
    fun destroy() {

    }
}