package com.app.repositories.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.app.interfaces.base.ParseErrors
import com.app.interfaces.models.*
import com.app.interfaces.models.common.LiveResponse
import com.app.interfaces.repository.AuthRepository
import com.app.repositories.local.dao.CommonDao
import com.app.repositories.remote.api.AuthApi
import com.app.repositories.utils.PreferencesHelper


class AuthRepositoryImp constructor(private val api: AuthApi,
                                    private val prefsHelper: PreferencesHelper,
                                    parseErrors: ParseErrors) : BaseRepository(parseErrors),
    AuthRepository {
    
    override fun signIn(githubCode: String): LiveData<LiveResponse<AccessToken>> =
            Transformations.map(callApi { api.signIn(githubCode = githubCode) }){
                it.data?.let { prefsHelper.saveAccessToken(it.accessToken) }
                it
            }
    
}