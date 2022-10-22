package com.farooq.lastfm.presentation.album_info.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.farooq.lastfm.domain.model.AlbumInfo

class TracksAdapter : ListAdapter<AlbumInfo.Track, TracksViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        return TracksViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<AlbumInfo.Track>() {
            override fun areItemsTheSame(oldItem: AlbumInfo.Track, newItem: AlbumInfo.Track): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: AlbumInfo.Track, newItem: AlbumInfo.Track): Boolean {
                return oldItem == newItem
            }
        }
    }
}