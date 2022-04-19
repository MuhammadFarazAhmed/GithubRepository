package com.app.interfaces.usecases

import androidx.lifecycle.LiveData
import com.app.interfaces.models.AccessToken
import com.app.interfaces.models.common.LiveResponse

interface SignInUsecase {

    fun signIn(githubCode:String): LiveData<LiveResponse<AccessToken>>
}