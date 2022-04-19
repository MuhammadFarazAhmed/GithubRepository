package com.app.repositories.usecases

import androidx.lifecycle.LiveData
import com.app.interfaces.models.User
import com.app.interfaces.models.common.LiveResponse
import com.app.interfaces.repository.UserRepository
import com.app.interfaces.usecases.UserUseCase

class UserUsecaseImp constructor(private val userRepository: UserRepository) : BaseUsecase(),
    UserUseCase {
    
    override fun getUserProfile(): LiveData<LiveResponse<User>> = userRepository.getUserProfile()
    
    override fun logout() {
        userRepository.logout()
    }
}