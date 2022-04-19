package com.app.upmworker.auth

import androidx.work.WorkManager
import com.app.interfaces.repository.AuthRepository
import com.app.interfaces.usecases.*
import com.app.repositories.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Suppress("unused") @Module @InstallIn(ViewModelComponent::class) class AuthModule {
    
    @Provides @ViewModelScoped
    fun provideSignInUsecase(authRepository: AuthRepository): SignInUsecase =
            SignInUsecaseImp(authRepository)
    
    
}