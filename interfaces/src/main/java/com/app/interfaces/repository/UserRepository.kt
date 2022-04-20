package com.app.interfaces.repository

import androidx.lifecycle.LiveData
import com.app.interfaces.models.Repository
import com.app.interfaces.models.User
import com.app.interfaces.models.common.LiveResponse

interface UserRepository {
    
    fun getUserProfile(): LiveData<LiveResponse<User>>
    
    suspend fun getUserRepos(page: Int, itemsPerPage: Int?): List<Repository>
    
    suspend fun getUserStarredRepos(page: Int, itemsPerPage: Int?): List<Repository>
    
    fun logout()
}