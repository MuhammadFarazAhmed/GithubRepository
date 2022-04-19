package com.app.base.vm

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData


abstract class SimpleListViewModel<Item> constructor(application: Application) :
    BaseViewModel(application) {

    val items = MutableLiveData<List<Item>>()
    val title = ObservableField("")
    val isBackButtonVisible = ObservableBoolean(false)
    val isSaveButtonVisible = ObservableBoolean(false)

    abstract fun getItems(updateView: () -> Unit)

    protected fun onLoading(updateView: () -> Unit): (boolean: Boolean) -> Unit {
        return {
            onLoading(it)
            updateView.invoke()
        }
    }

    protected fun onSuccess(updateView: () -> Unit): (List<Item>) -> Unit {
        return {
            items.value = it
            onSuccess()
            updateView.invoke()
        }
    }
}
