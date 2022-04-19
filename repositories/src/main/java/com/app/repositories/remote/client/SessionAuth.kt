package com.app.repositories.remote.client

import com.app.repositories.utils.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class SessionAuth(private val preferencesHelper: PreferencesHelper) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var newRequest = chain.request()
        if (preferencesHelper.getAccessToken() != "") {
            newRequest = chain.request().newBuilder().addHeader(stokenParamName,
                            if (preferencesHelper.getAccessToken() == "") "" else "Bearer " + preferencesHelper.getAccessToken())
                    .build()
        }
        return chain.proceed(newRequest)
    }

    companion object {

        private val location = "header"

        //  private val sidParamName = "sid"
        private val stokenParamName = "Authorization"
    }
}
