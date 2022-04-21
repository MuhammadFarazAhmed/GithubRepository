package com.app.interfaces.repository

import com.app.interfaces.models.Repository

interface RepoRepository {
    
    suspend fun getUserRepos(page: Int, itemsPerPage: Int?): List<Repository>
    
    suspend fun getUserStarredRepos(page: Int, itemsPerPage: Int?): List<Repository>
    
}