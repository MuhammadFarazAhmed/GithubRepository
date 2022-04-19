package com.app.repositories.remote.client


import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

import java.io.IOException

class ChangeUrlIntercepter : Interceptor {
    private var mScheme: String? = null
    private var mHost: String? = null

    fun setInterceptor(url: String) {
        val httpUrl = url.toHttpUrlOrNull()
        mScheme = httpUrl?.scheme
        mHost = httpUrl?.host
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()

        // If new Base URL is properly formatted than replace with old one
        if (mScheme != null && mHost != null) {
            val newUrl = original.url.newBuilder()
                .scheme(mScheme!!)
                .host(mHost!!)
                .build()
            original = original.newBuilder()
                .url(newUrl)
                .build()
        }
        return chain.proceed(original)
    }
}// Intentionally blank