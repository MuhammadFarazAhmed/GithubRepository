package com.app.repositories.repos

import androidx.lifecycle.LiveData
import com.app.interfaces.base.ParseErrors
import com.app.interfaces.models.Repository
import com.app.interfaces.repository.RepoRepository
import com.app.repositories.remote.api.RepoApi
import com.app.repositories.utils.PreferencesHelper


class RepoRepositoryImp constructor(private val api: RepoApi,
                                    private val prefsHelper: PreferencesHelper,
                                    parseErrors: ParseErrors) : BaseRepository(parseErrors),
    RepoRepository {
    
    override suspend fun getUserRepos(page: Int, itemsPerPage: Int?): List<Repository>  =
            api.getUserRepos(prefsHelper.user.value!!.login, page, itemsPerPage)
    
    override suspend fun getUserStarredRepos(page: Int, itemsPerPage: Int?): List<Repository> =
            api.getUserStarredRepos(prefsHelper.user.value!!.login, page, itemsPerPage)
    
    override fun hasUser(): LiveData<Boolean>  =prefsHelper.isLoggedIn
    
}