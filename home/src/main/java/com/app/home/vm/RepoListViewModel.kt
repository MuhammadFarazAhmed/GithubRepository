package com.app.home.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.app.base.vm.BaseViewModel
import com.app.interfaces.models.Repository
import com.app.interfaces.usecases.RepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class RepoListViewModel @Inject constructor(application: Application,
                                                           private val repoUseCase: RepoUseCase) :
    BaseViewModel(application) {
    
    val repos = MutableLiveData<PagingData<Repository>>()
    val starredRepos = MutableLiveData<PagingData<Repository>>()
    
    fun getUserRepos() {
        viewModelScope.launch {
            repoUseCase.getUserRepos(viewModelScope).collect {
                repos.value = it
            }
        }
    }
    
    fun getUserStarredRepos() {
        viewModelScope.launch {
            repoUseCase.getUserStarredRepos(viewModelScope).collect {
                starredRepos.value = it
            }
        }
    }
    
    fun hasUser() = repoUseCase.hasUser()
    
    
}