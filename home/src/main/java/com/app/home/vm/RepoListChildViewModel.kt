package com.app.home.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.app.base.vm.BaseViewModel
import com.app.interfaces.models.Repository
import com.app.interfaces.usecases.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel class RepoListChildViewModel @Inject constructor(application: Application,
                                                                private val userUseCase: UserUseCase) :
    BaseViewModel(application) {
    
    val repos = MutableLiveData<PagingData<Repository>>()
    val starredRepos = MutableLiveData<PagingData<Repository>>()
    
    suspend fun getUserRepos() {
        userUseCase.getUserRepos(viewModelScope).collect {
            repos.value = it
        }
    }
    
    suspend fun getUserStarredRepos() {
        userUseCase.getUserStarredRepos(viewModelScope).collect {
            starredRepos.value = it
        }
    }
    
    
}