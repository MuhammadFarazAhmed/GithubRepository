package com.app.repositories.usecases

import androidx.paging.*
import com.app.interfaces.models.Repository
import com.app.interfaces.repository.RepoRepository
import com.app.interfaces.usecases.RepoUseCase
import com.app.repositories.paging.BasePagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class RepoUsecaseImp constructor(private val repoRepository: RepoRepository) : BaseUsecase(),
    RepoUseCase {
    
    override fun getUserRepos(coroutineScope: CoroutineScope): Flow<PagingData<Repository>> =
            Pager(PagingConfig(pageSize = 10, prefetchDistance = 2)) {
                BasePagingSource { page: Int, itemsPerPage: Int ->
                    repoRepository.getUserRepos(page, itemsPerPage)
                }
            }.flow.cachedIn(coroutineScope)
    
    override fun getUserStarredRepos(coroutineScope: CoroutineScope): Flow<PagingData<Repository>> =
            Pager(PagingConfig(pageSize = 10, prefetchDistance = 2)) {
                BasePagingSource { page: Int, itemsPerPage: Int ->
                    repoRepository.getUserStarredRepos(page, itemsPerPage)
                }
            }.flow.cachedIn(coroutineScope)
}