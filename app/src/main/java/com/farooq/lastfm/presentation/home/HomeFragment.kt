package com.farooq.lastfm.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.farooq.core.domain.ProgressBarState
import com.farooq.lastfm.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AlbumsAdapter()

        binding.albumsList.apply {
            layoutManager = GridLayoutManager(context, 2)
            hasFixedSize()
        }
        binding.albumsList.adapter = adapter

        viewModel.uiState.onEach {
            when (it) {
                is HomeUIState.UpdateAlbum -> {
                    binding.albumsList.isVisible = true
                    binding.emptyList.isVisible = false
                    adapter.submitList(it.album)
                }
                is HomeUIState.Loading -> binding.progressBar.isVisible = it.progressBarState == ProgressBarState.Loading
                is HomeUIState.Error -> {}
                is HomeUIState.Nothing -> {
                    binding.albumsList.isVisible = false
                    binding.emptyList.isVisible = true
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.onEvent(HomeEvent.GetAlbums)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}