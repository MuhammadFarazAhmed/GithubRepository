package com.app.repositories.remote.client


import com.app.repositories.BuildConfig
import com.app.repositories.utils.PreferencesHelper
import okhttp3.*
import java.io.IOException


class ChangeBaseUrl(private val preferencesHelper: PreferencesHelper) : Interceptor {
    
    @Throws(IOException::class) override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest: Request
        if (preferencesHelper.getAccessToken() == "") {
            newRequest = request.newBuilder()
                    .url(request.url.newBuilder().host(BuildConfig.BASE_URL_OAUTH).build()).build()
        } else {
            newRequest = request.newBuilder()
                    .url(request.url.newBuilder().host(BuildConfig.BASE_URL).build()).build()
        }
        
        return chain.proceed(newRequest)
    }
    
    companion object {}
}
