package com.app.repositories.usecases

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.app.interfaces.models.Repository
import com.app.interfaces.models.User
import com.app.interfaces.models.common.LiveResponse
import com.app.interfaces.repository.UserRepository
import com.app.interfaces.usecases.UserUseCase
import com.app.repositories.paging.BasePagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class UserUsecaseImp constructor(private val userRepository: UserRepository) : BaseUsecase(),
    UserUseCase {
    
    override fun getUserProfile(): LiveData<LiveResponse<User>> = userRepository.getUserProfile()
    
    override fun getUserRepos(coroutineScope: CoroutineScope): Flow<PagingData<Repository>> =
            Pager(PagingConfig(pageSize = 10, prefetchDistance = 2)) {
                BasePagingSource { page: Int, itemsPerPage: Int ->
                    userRepository.getUserRepos(page, itemsPerPage)
                }
            }.flow.cachedIn(coroutineScope)
    
    override fun getUserStarredRepos(coroutineScope: CoroutineScope): Flow<PagingData<Repository>> =
            Pager(PagingConfig(pageSize = 10, prefetchDistance = 2)) {
                BasePagingSource { page: Int, itemsPerPage: Int ->
                    userRepository.getUserStarredRepos(page, itemsPerPage)
                }
            }.flow.cachedIn(coroutineScope)
    
    override fun logout() {
        userRepository.logout()
    }
}