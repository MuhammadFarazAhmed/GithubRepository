package com.app.base.callback

import com.app.base.utils.NetworkStateListCallback

interface SimpleListViewCallback: NetworkStateListCallback {
    fun onSaveButtonClicked()
    fun onBackPressed()
}
