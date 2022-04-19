package com.app.base.vm

import android.app.Application
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {


    val progress = ObservableBoolean(false)
    val error = ObservableField("")
    val errorType = ObservableInt(0)
    val listErrorViewVisible = ObservableBoolean(false)



    fun getString(@StringRes idRes: Int): String =
        getApplication<Application>().getString(idRes)

    fun getStringArray(@ArrayRes idRes: Int) =
        getApplication<Application>().resources.getStringArray(idRes)

    fun getColor(@ColorRes idRes: Int): Int =
        ContextCompat.getColor(getApplication(), idRes)

    fun getString(@StringRes idRes: Int, vararg input: Any): String =
        getApplication<Application>().getString(idRes, *input)

    fun cancelRequest() {
        viewModelScope.cancel()
    }


    protected fun onLoading(progress: Boolean) {
        this.progress.set(progress)
        if (progress) {
            this.error.set("")
            this.errorType.set(0)
        }
        this.listErrorViewVisible.set(true)
    }

    protected fun onSuccess() {
        this.error.set("")
        this.errorType.set(0)
        this.listErrorViewVisible.set(false)
    }

    protected fun onError(code: Int, error: String) {
        this.error.set(error)
        this.errorType.set(code)
        this.listErrorViewVisible.set(true)
    }

}