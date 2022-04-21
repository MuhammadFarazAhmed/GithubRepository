package com.app.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.base.callback.RecyclerViewCallback
import com.app.base.callback.RecyclerViewItemCallback
import com.app.interfaces.models.User
import com.app.search.databinding.UserListItemBinding
import com.app.search.vm.UserItemViewModel

class UserListAdapter(private val callback: RecyclerViewCallback<User>) :
    PagingDataAdapter<User, UserListAdapter.UserViewHolder>(UserComparator) {
    
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
        
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(UserListItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
    }
    
    inner class UserViewHolder(binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root), RecyclerViewItemCallback<User> {
        private val vm = UserItemViewModel(binding.root.context)
        
        init {
            binding.cb = this
            binding.vm = vm
            binding.executePendingBindings()
        }
        
        fun bind(user: User) {
            vm.setData(user)
        }
        
        override fun onListItemClicked(item: User) {
            callback.onListItemClicked(item, absoluteAdapterPosition)
        }
    }
    
    object UserComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
    
}