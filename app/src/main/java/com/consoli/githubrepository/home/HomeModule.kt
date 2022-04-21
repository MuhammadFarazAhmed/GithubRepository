package com.consoli.githubrepository.home

import com.app.interfaces.repository.RepoRepository
import com.app.interfaces.repository.UserRepository
import com.app.interfaces.usecases.RepoUseCase
import com.app.interfaces.usecases.UserUseCase
import com.app.repositories.usecases.RepoUsecaseImp
import com.app.repositories.usecases.UserUsecaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Suppress("unused") @Module @InstallIn(ViewModelComponent::class) class HomeModule {
    
    @Provides @ViewModelScoped fun provideUserUsecase(userRepository: UserRepository): UserUseCase =
            UserUsecaseImp(userRepository)
    
    @Provides @ViewModelScoped fun provideRepoUsecase(repoRepository: RepoRepository): RepoUseCase =
            RepoUsecaseImp(repoRepository)
}