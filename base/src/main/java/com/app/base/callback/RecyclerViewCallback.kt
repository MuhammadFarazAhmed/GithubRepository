package com.app.base.callback

interface RecyclerViewCallback<T> {
    fun onListItemClicked(item : T,position: Int)
}