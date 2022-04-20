package com.app.repositories.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.app.interfaces.base.ParseErrors
import com.app.interfaces.models.Repository
import com.app.interfaces.models.User
import com.app.interfaces.models.common.LiveResponse
import com.app.interfaces.repository.UserRepository
import com.app.repositories.remote.api.UserApi
import com.app.repositories.utils.PreferencesHelper


class UserRepositoryImp constructor(private val api: UserApi,
                                    private val prefsHelper: PreferencesHelper,
                                    parseErrors: ParseErrors) : BaseRepository(parseErrors),
    UserRepository {
    
    override fun getUserProfile(): LiveData<LiveResponse<User>> =
            Transformations.map(callApi { api.getUserProfile() }) {
                it.data?.let { prefsHelper.saveUser(it) }
                it
            }
    
    override suspend fun getUserRepos(page: Int, itemsPerPage: Int?): List<Repository>  =
            api.getUserRepos(prefsHelper.user.value!!.login, page, itemsPerPage)
    
    override suspend fun getUserStarredRepos(page: Int, itemsPerPage: Int?): List<Repository> =
            api.getUserStarredRepos(prefsHelper.user.value!!.login, page, itemsPerPage)
    
    override fun logout() {
        prefsHelper.removeAuth()
    }
    
}