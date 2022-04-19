package com.app.repositories.remote.api


import com.app.interfaces.BuildConfig
import com.app.interfaces.models.*
import com.app.interfaces.models.common.Message
import retrofit2.Response
import retrofit2.http.*
import javax.annotation.Nullable

interface AuthApi {
    
    @FormUrlEncoded @Headers("Accept: application/json") @POST("login/oauth/access_token")
    suspend fun signIn(
        @Field("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Field("client_secret") clientSecret: String = BuildConfig.CLEINT_SECRET,
        @Field("code") githubCode: String
                      ): AccessToken
}