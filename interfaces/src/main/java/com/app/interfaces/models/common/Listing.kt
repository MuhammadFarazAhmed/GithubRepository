package com.app.interfaces.models.common

import androidx.lifecycle.LiveData
import androidx.paging.PagingData

data class Listing<T : Any>(
    val pagedListLiveData: LiveData<PagingData<T>>,
    val initLoadLiveData: LiveData<PageStatus>,
    val pageLoadLiveData: LiveData<PageState>
)