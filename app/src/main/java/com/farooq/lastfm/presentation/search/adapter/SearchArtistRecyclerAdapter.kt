package com.farooq.lastfm.presentation.search.adapter

import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.farooq.lastfm.R
import com.farooq.lastfm.data.local.model.SearchArtist
import com.farooq.lastfm.presentation.top_albums.TopAlbumsFragment


class SearchArtistRecyclerAdapter : PagingDataAdapter<SearchArtist, SearchArtistViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {
        return SearchArtistViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SearchArtistViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
            holder.binding.root.setOnClickListener {
                it.findNavController().navigate(R.id.topAlbumsFragment, TopAlbumsFragment.createBundle(item.name))
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<SearchArtist>() {
            override fun areItemsTheSame(oldItem: SearchArtist, newItem: SearchArtist): Boolean {
                return oldItem.name == newItem.name && oldItem.mbid == newItem.mbid
            }

            override fun areContentsTheSame(oldItem: SearchArtist, newItem: SearchArtist): Boolean {
                return oldItem == newItem
            }
        }
    }
}
