package com.app.home.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.app.base.vm.BaseViewModel
import com.app.interfaces.models.User
import com.app.interfaces.usecases.SignInUsecase
import com.app.interfaces.usecases.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel class UserViewModel @Inject constructor(application: Application,
                                                       private val userUseCase: UserUseCase) :
    BaseViewModel(application) {
    
    val user = MutableLiveData<User>()
    
    fun getUserProfile() = Transformations.map(userUseCase.getUserProfile()) {
        it?.data?.let {
            user.value = it
        }
        it.callInfo
    }
    
    fun logout() {
        userUseCase.logout()
    }
}
   