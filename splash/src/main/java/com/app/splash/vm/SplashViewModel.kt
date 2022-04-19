package com.app.splash.vm

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.app.base.vm.BaseViewModel
import com.app.interfaces.usecases.SplashUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    application: Application,
    private val splashUsecase: SplashUsecase
) :
    BaseViewModel(application) {
    
    fun isLoggedIn() = splashUsecase.isLoggedIn()
    
}
