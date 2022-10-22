package com.farooq.lastfm.presentation.top_albums.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.farooq.lastfm.R
import com.farooq.lastfm.data.remote.top_albums.dto.Album
import com.farooq.lastfm.databinding.SearchArtistListItemBinding


class TopAlbumViewHolder(val binding: SearchArtistListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(album: Album) {
        binding.image.load(album.image[0].text) {
            error(R.drawable.ic_album_placeholder)
            placeholder(R.drawable.ic_album_placeholder)
        }
        binding.name.text = album.name
        binding.streamable.text = album.artist.name
    }

    companion object {
        fun create(parent: ViewGroup): TopAlbumViewHolder {
            val binding = SearchArtistListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TopAlbumViewHolder(binding)
        }
    }
}