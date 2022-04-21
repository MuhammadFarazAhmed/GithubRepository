package com.app.search.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.app.base.vm.BaseViewModel
import com.app.interfaces.models.User
import com.app.interfaces.usecases.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class SearchViewModel @Inject constructor(application: Application,
                                                         private val userUseCase: UserUseCase) :
    BaseViewModel(application) {
    
    var query = MutableLiveData("A")
    
    val users = MutableLiveData<PagingData<User>>()
    
    fun searchUsers() {
        viewModelScope.launch {
            userUseCase.searchUsers(query.value!!, viewModelScope).collect {
                users.value = it
            }
        }
    }
    
    fun setSearchArg(query: String) {
        this.query.value = query
    }
    
    
}