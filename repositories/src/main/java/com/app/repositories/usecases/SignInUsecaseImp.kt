package com.app.repositories.usecases

import com.app.interfaces.repository.AuthRepository
import com.app.interfaces.usecases.SignInUsecase
import com.app.repositories.utils.setState
import kotlinx.coroutines.launch

class SignInUsecaseImp constructor(private val authRepository: AuthRepository) : BaseUsecase(),
    SignInUsecase {
    
    override fun signIn(githubCode: String) = authRepository.signIn(githubCode)
}