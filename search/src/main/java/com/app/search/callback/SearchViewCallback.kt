package com.app.search.callback

import com.app.interfaces.models.Repository
import com.app.interfaces.models.User

interface SearchViewCallback {
    fun onSearchClicked()
    fun onCancelSearchClicked()
    fun onListItemClicked(item: Repository, position: Int)
    fun onListItemClicked(item: User, position: Int)
}