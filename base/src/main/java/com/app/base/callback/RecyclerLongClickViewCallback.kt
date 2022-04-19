package com.app.base.callback

interface RecyclerLongClickViewCallback<T> : RecyclerViewCallback<T> {
    fun onItemLongClick(item: T, position: Int):Boolean
}