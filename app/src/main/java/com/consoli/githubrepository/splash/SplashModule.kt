package com.consoli.githubrepository.splash

import androidx.work.WorkManager
import com.app.interfaces.repository.AuthRepository
import com.app.interfaces.usecases.SplashUsecase
import com.app.repositories.usecases.SplashUsecaseImp
import com.app.repositories.utils.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class SplashModule {

    @Provides
    @ViewModelScoped
    fun provideSplashUsecase(
        preferencesHelper: PreferencesHelper
    ): SplashUsecase =
        SplashUsecaseImp(preferencesHelper)

}