package com.farooq.lastfm.presentation.top_albums

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.farooq.lastfm.databinding.FragmentTopAlbumsBinding
import com.farooq.lastfm.presentation.album_info.ARG_ARTIST
import com.farooq.lastfm.presentation.top_albums.adapter.TopAlbumsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopAlbumsFragment : Fragment() {

    private var _binding: FragmentTopAlbumsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<TopAlbumsViewModel>()
    private lateinit var adapter : TopAlbumsAdapter
    private var artistName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            artistName = requireArguments().getString(ARG_ARTIST).toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTopAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewModel.onEvent(TopAlbumEvent.GetTopAlbums(artistName))
        viewModel.dataState.onEach { state ->
            when {
                state.albums != null -> {
                    adapter.submitData(viewLifecycleOwner.lifecycle, state.albums)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initAdapter(){
        adapter = TopAlbumsAdapter()
        binding.list.apply {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))
        }

        initPagingLoadState()

        binding.retryButton.setOnClickListener {
            adapter.retry()
        }
    }


    private fun initPagingLoadState(){
        val header = com.farooq.core.components.PagingLoadStateAdapter { adapter.retry() }
        binding.list.adapter = adapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = com.farooq.core.components.PagingLoadStateAdapter { adapter.retry() }
        )

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->

                header.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && adapter.itemCount > 0 }
                    ?: loadState.prepend

                // Show loading spinner during initial load or refresh.
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

                handleError(loadState)

                val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                // show empty list
                binding.emptyList.isVisible = isListEmpty
                // Only show the list if refresh succeeds, either from the the local db or the remote.
                binding.list.isVisible = loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
            }
        }
    }

    // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
        errorState?.let {
            Toast.makeText(requireContext(), "${it.error})", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun createBundle(name : String) = bundleOf(
            Pair(ARG_ARTIST,name)
        )
    }

}