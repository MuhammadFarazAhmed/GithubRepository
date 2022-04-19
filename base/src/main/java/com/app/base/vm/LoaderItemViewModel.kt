package com.app.base.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.app.interfaces.models.common.PageState
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class LoaderItemViewModel @Inject constructor() :
    ViewModel() {
    val data = ObservableField(PageState.SUCCESS)


    fun setData(pageState: PageState) {
        data.set(pageState)
    }

}