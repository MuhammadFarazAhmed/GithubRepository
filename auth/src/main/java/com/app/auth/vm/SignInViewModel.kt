package com.app.auth.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.app.base.R

import com.app.base.vm.BaseViewModel
import com.app.interfaces.usecases.SignInUsecase
import com.mlykotom.valifi.ValiFiForm
import com.mlykotom.valifi.fields.ValiFieldEmail
import com.mlykotom.valifi.fields.ValiFieldText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel class SignInViewModel @Inject constructor(application: Application,
                                                         private val signInUsecase: SignInUsecase) :
    BaseViewModel(application) {
    
    val githubCode = MutableLiveData<String>()
    
    fun signIn() = Transformations.map(signInUsecase.signIn(githubCode.value!!)) {
        it.callInfo
    }
    
    
}