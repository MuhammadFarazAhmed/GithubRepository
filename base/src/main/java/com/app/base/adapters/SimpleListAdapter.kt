package com.app.base.adapters


import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.base.callback.RecyclerViewCallback
import com.app.base.callback.RecyclerViewItemCallback


abstract class SimpleListAdapter<Item, bind : ViewDataBinding, VH : SimpleListAdapter<Item, bind, VH>.SimpleViewHolder<bind>> constructor(
    private val callback: RecyclerViewCallback<Item>? = null,
    diffCallback: DiffUtil.ItemCallback<Item>
) : ListAdapter<Item, VH>(diffCallback) {


    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    abstract inner class SimpleViewHolder<bind : ViewDataBinding>(private val binding: bind) :
        RecyclerView.ViewHolder(binding.root),
        RecyclerViewItemCallback<Item> {

        abstract fun bind(item: Item)

        override fun onListItemClicked(item: Item) {
            callback?.onListItemClicked(item, absoluteAdapterPosition)
        }
    }
}


