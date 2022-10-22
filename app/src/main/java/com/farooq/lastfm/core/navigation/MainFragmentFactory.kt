package com.farooq.lastfm.core.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.farooq.core.components.dialog.ErrorSheetFragment
import com.farooq.lastfm.presentation.album_info.AlbumInfoFragment
import com.farooq.lastfm.presentation.home.HomeFragment
import com.farooq.lastfm.presentation.search.SearchArtistFragment
import com.farooq.lastfm.presentation.top_albums.TopAlbumsFragment
import javax.inject.Inject

class MainFragmentFactory @Inject constructor() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            HomeFragment::class.java.name -> {
                HomeFragment()
            }
            AlbumInfoFragment::class.java.name -> {
                AlbumInfoFragment()
            }
            SearchArtistFragment::class.java.name -> {
                SearchArtistFragment()
            }
            TopAlbumsFragment::class.java.name -> {
                TopAlbumsFragment()
            }
            ErrorSheetFragment::class.java.name -> {
                ErrorSheetFragment()
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}