package com.app.repositories.remote.api


import com.app.interfaces.models.User
import com.app.interfaces.models.common.PagingItem
import retrofit2.http.*

interface UserApi {
    
    @Headers("Accept: application/json") @GET("user") suspend fun getUserProfile(): User
    
    @Headers("Accept: application/json") @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String ="A", @Query("page") page: Int, @Query("items_per_page") itemsPerPage: Int?): PagingItem<User>
    
}