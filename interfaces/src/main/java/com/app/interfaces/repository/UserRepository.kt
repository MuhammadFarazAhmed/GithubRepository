package com.app.interfaces.repository

import androidx.lifecycle.LiveData
import com.app.interfaces.models.User
import com.app.interfaces.models.common.LiveResponse
import com.app.interfaces.models.common.PagingItem

interface UserRepository {
    
    fun getUserProfile(): LiveData<LiveResponse<User>>
    
    suspend fun searchUsers(query: String, page: Int, itemsPerPage: Int): PagingItem<User>
    
    fun logout()
}