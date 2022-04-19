package com.app.repositories.usecases

import com.app.interfaces.repository.AuthRepository
import com.app.interfaces.usecases.SignInUsecase

class SignInUsecaseImp constructor(private val authRepository: AuthRepository) : BaseUsecase(),
    SignInUsecase {
    
    override fun signIn(githubCode: String) = authRepository.signIn(githubCode)
}