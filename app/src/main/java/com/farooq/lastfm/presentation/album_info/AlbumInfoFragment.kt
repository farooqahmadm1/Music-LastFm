package com.farooq.lastfm.presentation.album_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.farooq.core.domain.ProgressBarState
import com.farooq.lastfm.R
import com.farooq.lastfm.databinding.FragmentAlbumInfoBinding
import com.farooq.lastfm.domain.model.AlbumInfo
import com.farooq.lastfm.presentation.album_info.adapter.TracksAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

const val ARG_ARTIST = "ARTIST_NAME"
const val ARG_ALBUM = "ALBUM_NAME"
const val ARG_SEARCH = "IS_FROM_SEARCH"

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class AlbumInfoFragment : Fragment() {

    private var _binding: FragmentAlbumInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private val viewModel by viewModels<AlbumInfoViewModel>()
    private lateinit var controller: NavController
    private lateinit var adapter: TracksAdapter

    private var artistName = ""
    private var albumName = ""
    private var isFromSearch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            artistName = it.getString(ARG_ARTIST) ?: ""
            albumName = it.getString(ARG_ALBUM) ?: ""
            isFromSearch = it.getBoolean(ARG_SEARCH)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAlbumInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initPagingAdapter()

        controller = findNavController()

        viewModel.onEvent(AlbumInfoEvent.GetAlbumInfo(artistName, albumName))
        viewModel.uiState.onEach { state ->
            when (state) {
                is AlbumUIState.UpdateAlbum -> binding.bindAlbum(state.album)
                is AlbumUIState.DeleteAlbumSuccess -> controller.popBackStack()
                is AlbumUIState.Loading -> binding.progressBar.isVisible = ProgressBarState.Loading == state.progressBarState
                is AlbumUIState.Error -> findNavController().navigate(R.id.errorSheetFragment)
                is AlbumUIState.Nothing -> controller.popBackStack()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initView() {
        binding.deleteAlbumBtn.isVisible = !isFromSearch
        binding.deleteAlbumBtn.setOnClickListener {
            viewModel.dataState.value.album?.let { viewModel.onEvent(AlbumInfoEvent.DeleteAlbumInfo(it.name)) }
        }
    }

    private fun initPagingAdapter() {
        adapter = TracksAdapter()
        binding.trackList.apply {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))
        }
        binding.trackList.adapter = adapter
    }

    private fun FragmentAlbumInfoBinding.bindAlbum(albumInfo: AlbumInfo) {
        name.text = albumInfo.name
        streamable.text = albumInfo.artist
        image.load(albumInfo.image) {
            error(R.drawable.ic_album_placeholder)
            placeholder(R.drawable.ic_album_placeholder)
        }
        if (!albumInfo.tracks.isNullOrEmpty()) {
            adapter.submitList(albumInfo.tracks)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun createBundle(artistName: String, albumName: String,isFromSearch : Boolean = false) = bundleOf(
            Pair(ARG_ARTIST, artistName),
            Pair(ARG_ALBUM, albumName),
            Pair(ARG_SEARCH, isFromSearch)
        )
    }
}