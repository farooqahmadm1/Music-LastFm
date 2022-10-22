package com.farooq.lastfm.presentation.home

import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.farooq.lastfm.R
import com.farooq.lastfm.domain.model.Album
import com.farooq.lastfm.presentation.album_info.AlbumInfoFragment

class AlbumsAdapter : ListAdapter<Album, AlbumViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
            holder.binding.root.setOnClickListener {
                it.findNavController().navigate(R.id.albumInfoFragment, AlbumInfoFragment.createBundle(item.artist,item.name))
            }
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
                return oldItem == newItem
            }
        }
    }
}