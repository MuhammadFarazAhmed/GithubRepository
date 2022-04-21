package com.app.repositories.usecases

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.app.interfaces.models.User
import com.app.interfaces.models.common.LiveResponse
import com.app.interfaces.models.common.PagingItem
import com.app.interfaces.repository.UserRepository
import com.app.interfaces.usecases.UserUseCase
import com.app.repositories.paging.UserBasePagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class UserUsecaseImp constructor(private val userRepository: UserRepository) : BaseUsecase(),
    UserUseCase {
    
    override fun getUserProfile(): LiveData<LiveResponse<User>> = userRepository.getUserProfile()
    
    override suspend fun searchUsers(query: String,
                                     coroutineScope: CoroutineScope): Flow<PagingData<User>> =
            Pager(PagingConfig(pageSize = 10, prefetchDistance = 2)) {
                UserBasePagingSource { page, itemsPerPage ->
                    val response = userRepository.searchUsers(query, page, itemsPerPage)
                    PagingItem(response.totalCount, response.incompleteResults, response.items)
                }
            }.flow.cachedIn(coroutineScope)
    
    
    override fun logout() {
        userRepository.logout()
    }
}