package com.farooq.lastfm.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.farooq.lastfm.R
import com.farooq.lastfm.databinding.ItemAlbumLayoutBinding
import com.farooq.lastfm.domain.model.Album
import com.farooq.lastfm.domain.model.AlbumInfo

class AlbumViewHolder(val binding: ItemAlbumLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(albumInfo: Album) {
        binding.albumNameTxt.text = albumInfo.name
        binding.artistNameTxt.text = albumInfo.artist
        binding.image.load(albumInfo.image){
            error(R.drawable.ic_album_placeholder)
            placeholder(R.drawable.ic_album_placeholder)
        }
    }

    companion object {
        fun create(parent: ViewGroup): AlbumViewHolder {
            val binding = ItemAlbumLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AlbumViewHolder(binding)
        }
    }
}