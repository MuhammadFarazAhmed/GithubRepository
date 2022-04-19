package com.app.repositories.usecases

import androidx.lifecycle.LiveData
import com.app.interfaces.usecases.SplashUsecase
import com.app.repositories.utils.PreferencesHelper

class SplashUsecaseImp constructor(private val preferencesHelper: PreferencesHelper) :
    BaseUsecase(), SplashUsecase {
    
    override fun isLoggedIn(): LiveData<Boolean> = preferencesHelper.isLoggedIn
    
    
}