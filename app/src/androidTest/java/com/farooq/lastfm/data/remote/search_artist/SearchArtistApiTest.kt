package com.farooq.lastfm.data.remote.search_artist

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
class SearchArtistApiTest {


    @get:Rule
    val rule = HiltAndroidRule(this)

    @Inject
    lateinit var api: SearchArtistApi

    @Before
    fun setUp() {
        rule.inject()
    }

    @Test
    fun success_result_from_search_artist_api() = runBlocking {
        val result = api.getSearchArtists("cherry", 1, 1)
        assertThat(result.results!!.artistMatches!!.artist[0].name.toLowerCase()).contains("cherry")
    }

}