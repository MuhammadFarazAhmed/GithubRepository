package com.app.base.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData


class ConnectionLiveData(private val context: Context) : LiveData<Boolean>() {

    private val connectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    private val callback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), callback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(callback)
    }
}