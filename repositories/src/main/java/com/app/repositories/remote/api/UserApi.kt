package com.app.repositories.remote.api


import com.app.interfaces.BuildConfig
import com.app.interfaces.models.*
import com.app.interfaces.models.common.Message
import retrofit2.Response
import retrofit2.http.*
import javax.annotation.Nullable

interface UserApi {
    
    @Headers("Accept: application/json")
    @GET("user")
    suspend fun getUserProfile(): User
}