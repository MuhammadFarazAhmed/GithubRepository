package com.app.base.utils

interface NetworkStateDialogCallback {
    fun onErrorDialogRetryButtonClicked(endpointTag: String)
    fun onErrorDialogClosed(endpointTag: String)
}
