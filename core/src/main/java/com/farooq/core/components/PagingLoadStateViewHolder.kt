package com.farooq.core.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.farooq.core.databinding.ItemLoadStateHeaderFooterViewBinding

class PagingLoadStateViewHolder(
    private val binding: ItemLoadStateHeaderFooterViewBinding,
    retry: () -> Unit
) : ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): PagingLoadStateViewHolder {
            val binding = ItemLoadStateHeaderFooterViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PagingLoadStateViewHolder(binding, retry)
        }
    }

}