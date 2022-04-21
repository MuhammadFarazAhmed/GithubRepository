package com.app.interfaces.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.app.interfaces.models.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface RepoUseCase {
    
    fun getUserRepos(coroutineScope: CoroutineScope): Flow<PagingData<Repository>>
    
    fun getUserStarredRepos(coroutineScope: CoroutineScope): Flow<PagingData<Repository>>
    
    fun hasUser(): LiveData<Boolean>
}