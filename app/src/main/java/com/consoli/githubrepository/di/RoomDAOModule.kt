package com.consoli.githubrepository.di

import com.app.repositories.local.GithubRepositoryDB
import com.app.repositories.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDAOModule {
//    @Provides
//    @Singleton
//    fun providePestDao(upmWorkerDB: UPMWorkerDB): PestDao = upmWorkerDB.pestDao()
//
//    @Provides
//    @Singleton
//    fun providePesticideDao(upmWorkerDB: UPMWorkerDB): PesticideDao = upmWorkerDB.pesticideDao()
//
//    @Provides
//    @Singleton
//    fun provideDeviceDao(upmWorkerDB: UPMWorkerDB): DevicesDao = upmWorkerDB.devicesDao()
//
//    @Provides
//    @Singleton
//    fun provideAnalysisPointDao(upmWorkerDB: UPMWorkerDB): AnalysisPointDao = upmWorkerDB.analysisPointDao()
//
//    @Provides
//    @Singleton
//    fun provideUserDao(upmWorkerDB: UPMWorkerDB): UserDao = upmWorkerDB.userDao()
//
//    @Provides
//    @Singleton
//    fun provideSiteDao(upmWorkerDB: UPMWorkerDB): SitesDao = upmWorkerDB.sitesDao()
//
    @Provides
    @Singleton
    fun provideCommonDao(githubRepositoryDB: GithubRepositoryDB): CommonDao = githubRepositoryDB.commonDao()
}