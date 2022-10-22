package com.farooq.lastfm.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.farooq.lastfm.data.local.model.SearchArtist
import com.farooq.lastfm.data.remote.search_artist.dto.ArtistDto
import com.farooq.lastfm.databinding.SearchArtistListItemBinding

class SearchArtistViewHolder(val binding: SearchArtistListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(artistDto: SearchArtist) {
        binding.image.load(artistDto.image?.get(0)?.text)
        binding.name.text = artistDto.name
        binding.streamable.text = artistDto.listeners
    }

    companion object {
        fun create(parent: ViewGroup): SearchArtistViewHolder {
            val binding = SearchArtistListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SearchArtistViewHolder(binding)
        }
    }
}