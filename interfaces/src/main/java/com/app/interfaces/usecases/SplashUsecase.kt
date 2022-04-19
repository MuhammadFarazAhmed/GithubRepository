package com.app.interfaces.usecases

import androidx.lifecycle.LiveData

interface SplashUsecase {
    fun isLoggedIn(): LiveData<Boolean>
}