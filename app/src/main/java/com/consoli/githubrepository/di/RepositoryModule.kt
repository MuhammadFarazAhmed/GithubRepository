package com.consoli.githubrepository.di

import com.app.interfaces.base.ParseErrors
import com.app.interfaces.repository.*
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

@Module @InstallIn(SingletonComponent::class) class RepositoryModule {
    
    @Provides @Singleton fun provideAuthApi(@Base retrofit: Retrofit): AuthApi =
            retrofit.create(AuthApi::class.java)
    
    @Provides @Singleton fun provideCommonApi(@Base retrofit: Retrofit): CommonApi =
            retrofit.create(CommonApi::class.java)
    
    @Provides @Singleton fun provideAuthRepository(authApi: AuthApi,
                                                   preferencesHelper: PreferencesHelper,
                                                   parseErrors: ParseErrors): AuthRepository =
            AuthRepositoryImp(authApi, preferencesHelper, parseErrors)
    
    @Provides @Singleton fun provideUserApi(@Base retrofit: Retrofit): UserApi =
            retrofit.create(UserApi::class.java)
    
    @Provides @Singleton fun provideUserRepository(userApi: UserApi,
                                                   preferencesHelper: PreferencesHelper,
                                                   parseErrors: ParseErrors): UserRepository =
            UserRepositoryImp(userApi, preferencesHelper, parseErrors)
    
    @Provides @Singleton fun provideRepoApi(@Base retrofit: Retrofit): RepoApi =
            retrofit.create(RepoApi::class.java)
    
    @Provides @Singleton fun provideRepoRepository(repoApi: RepoApi,
                                                   preferencesHelper: PreferencesHelper,
                                                   parseErrors: ParseErrors): RepoRepository =
            RepoRepositoryImp(repoApi, preferencesHelper, parseErrors)
    
}

// Here we provide a common repository that has @AppScope and used by multiple modules(by modules I mean Activity)
