package com.farooq.lastfm.presentation.album_info.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farooq.lastfm.databinding.ItemListTrackLayoutBinding
import com.farooq.lastfm.domain.model.AlbumInfo


class TracksViewHolder(private val binding: ItemListTrackLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: AlbumInfo.Track) {
        binding.nameTxt.text = track.name
        binding.artistNameTxt.text = track.artist
        binding.durationTxt.text = track.duration.toString()
    }

    companion object {
        fun create(parent: ViewGroup): TracksViewHolder {
            val binding = ItemListTrackLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TracksViewHolder(binding)
        }
    }
}