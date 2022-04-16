package com.app.upmworker.di

import com.app.interfaces.base.ParseErrors
import com.app.interfaces.repository.*
import com.app.repositories.local.dao.CommonDao
import com.app.repositories.remote.api.*
import com.app.repositories.repos.*
import com.app.repositories.utils.PreferencesHelper
import com.consoli.githubrepository.di.qualifier.Base
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthApi(@Base retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideCommonApi(@Base retrofit: Retrofit): CommonApi =
        retrofit.create(CommonApi::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi,
        commonDao: CommonDao,
        preferencesHelper: PreferencesHelper,
        parseErrors: ParseErrors
    ): AuthRepository =
        AuthRepositoryImp(authApi, commonDao, preferencesHelper, parseErrors)
    
}

// Here we provide a common repository that has @AppScope and used by multiple modules(by modules I mean Activity)
