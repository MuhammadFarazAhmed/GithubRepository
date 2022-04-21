package com.app.interfaces.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.app.interfaces.models.User
import com.app.interfaces.models.common.LiveResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    
    fun getUserProfile(): LiveData<LiveResponse<User>>
    
    suspend fun searchUsers(query: String, coroutineScope: CoroutineScope): Flow<PagingData<User>>
    
    fun logout()
}