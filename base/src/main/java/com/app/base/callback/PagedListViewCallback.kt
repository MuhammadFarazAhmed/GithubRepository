package com.app.base.callback

interface PagedListViewCallback<Item> : PagedAdapterCallback<Item>, SimpleListViewCallback {
    override fun onPageReload()

    override fun onListItemClicked(item: Item, position: Int)
    override fun onSaveButtonClicked()

    override fun onBackPressed()

    override fun onRetryButtonClicked()
}