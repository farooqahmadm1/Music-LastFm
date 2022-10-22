package com.farooq.lastfm.presentation.top_albums.adapter

import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.farooq.lastfm.R
import com.farooq.lastfm.data.remote.top_albums.dto.Album
import com.farooq.lastfm.presentation.album_info.AlbumInfoFragment


class TopAlbumsAdapter : PagingDataAdapter<Album, TopAlbumViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopAlbumViewHolder {
        return TopAlbumViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TopAlbumViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
            holder.binding.root.setOnClickListener {
                it.findNavController().navigate(R.id.albumInfoFragment, AlbumInfoFragment.createBundle(item.artist.name, item.name, true))
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
                return oldItem.name == newItem.name && oldItem.mbid == newItem.mbid
            }

            override fun areContentsTheSame(oldItem: Album, newItem: Album) = oldItem == newItem
        }
    }
}
