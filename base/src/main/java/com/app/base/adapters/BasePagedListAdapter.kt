package com.app.base.adapters

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.app.base.callback.PagedAdapterCallback
import com.app.base.ui.ViewHolder
import com.app.interfaces.models.common.PageState

abstract class BasePagedListAdapter<Item : Any> protected constructor(
    diffCallback: DiffUtil.ItemCallback<Item>, private val callback: PagedAdapterCallback<Item>?
) : PagingDataAdapter<Item, ViewHolder>(diffCallback) {

    protected var networkState: PageState? = null

    companion object {
        public const val TYPE_ITEM = 0
        public const val TYPE_PROGRESS = 1
    }

    protected fun hasExtraRow(): Boolean {
        return networkState != null && (networkState === PageState.LOADING || networkState === PageState.ERROR)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }
    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            TYPE_PROGRESS
        } else {
            TYPE_ITEM
        }
    }

    fun changePageState(newNetworkState: PageState) {
        val previousState = this.networkState
        val previousExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemChanged(itemCount - 1)
            } else {
                notifyItemChanged(itemCount - 1)
            }
        } else if (newExtraRow && previousState !== newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}
