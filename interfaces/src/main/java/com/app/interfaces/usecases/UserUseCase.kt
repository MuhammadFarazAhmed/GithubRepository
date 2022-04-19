package com.app.interfaces.usecases

import androidx.lifecycle.LiveData
import com.app.interfaces.models.AccessToken
import com.app.interfaces.models.User
import com.app.interfaces.models.common.LiveResponse

interface UserUseCase {
    
    fun getUserProfile(): LiveData<LiveResponse<User>>
    
    fun logout()
}