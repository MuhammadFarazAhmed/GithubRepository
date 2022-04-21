package com.app.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.base.callback.RecyclerViewCallback
import com.app.base.callback.RecyclerViewItemCallback
import com.app.home.databinding.RepoListItemBinding
import com.app.home.vm.RepoItemViewModel
import com.app.interfaces.models.Repository

class RepoListAdapter(
    private val callback: RecyclerViewCallback<Repository>
                     ) :
    PagingDataAdapter<Repository, RepoListAdapter.RepoItemHolder>(SiteComparator) {

    override fun onBindViewHolder(holder: RepoItemHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemHolder {
        return RepoItemHolder(
            RepoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class RepoItemHolder(binding: RepoListItemBinding) :
        RecyclerView.ViewHolder(binding.root), RecyclerViewItemCallback<Repository> {
        private val vm = RepoItemViewModel(binding.root.context)

        init {
            binding.cb = this
            binding.vm = vm
            binding.executePendingBindings()
        }

        fun bind(siteOutput: Repository) {
            vm.setData(siteOutput)
        }

        override fun onListItemClicked(item: Repository) {
            callback.onListItemClicked(item, absoluteAdapterPosition)
        }
    }

    object SiteComparator : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }
    }

}