package com.app.base.callback

interface RecyclerViewItemCallback<T> {
    fun onListItemClicked(item : T)
}