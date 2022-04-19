package com.app.base.vm

import android.app.Application
import androidx.databinding.ObservableField
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewImageViewModel @Inject constructor(application: Application) : BaseViewModel(application) {
    val image = ObservableField<Any>()
}
