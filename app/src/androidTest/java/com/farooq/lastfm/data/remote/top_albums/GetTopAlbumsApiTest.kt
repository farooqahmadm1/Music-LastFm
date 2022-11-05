package com.farooq.lastfm.data.remote.top_albums

import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@HiltAndroidTest
@RunWith(JUnit4::class)
class GetTopAlbumsApiTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Inject
    lateinit var getTopAlbumsApi: GetTopAlbumsApi

    @Before
    fun setUp() {
        rule.inject()
    }

    @Test
    fun success_result_from_top_album_info_api() = runBlocking {
        val result = getTopAlbumsApi.getTopAlbumByArtist("Cherry Vanilla", 1)
        assertThat("Cherry Vanilla").isEqualTo(result.topAlbums.album[0].artist.name)
    }

}