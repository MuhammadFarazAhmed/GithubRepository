package com.app.upmworker.home

import com.app.interfaces.repository.*
import com.app.interfaces.usecases.*
import com.app.repositories.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Suppress("unused") @Module @InstallIn(ViewModelComponent::class)
class HomeModule {
    
    @Provides @ViewModelScoped fun provideUserUsecase(userRepository: UserRepository): UserUseCase =
            UserUsecaseImp(userRepository)
}