package com.app.repositories.remote.api


import com.app.interfaces.models.Repository
import com.app.interfaces.models.User
import retrofit2.http.*

interface UserApi {
    
    @Headers("Accept: application/json") @GET("user") suspend fun getUserProfile(): User
    
    @Headers("Accept: application/json") @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") userName: String,  @Query("page") page: Int, @Query("items_per_page") itemsPerPage: Int?): List<Repository>
    
    @Headers("Accept: application/json") @GET("users/{username}/starred")
    suspend fun getUserStarredRepos(@Path("username") userName: String,  @Query("page") page: Int, @Query("items_per_page") itemsPerPage: Int?): List<Repository>
}