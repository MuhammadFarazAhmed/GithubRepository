package com.app.base.vm

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.Observer
import com.app.interfaces.models.common.Listing
import com.app.interfaces.models.common.PageState
import com.app.interfaces.models.common.PageStatus


abstract class PagedViewModel<Item : Any>(application: Application) : BaseViewModel(application) {
    private lateinit var listing: Listing<Item>
    val title = ObservableField("")
    val isBackButtonVisible = ObservableBoolean(false)
    val isSaveButtonVisible = ObservableBoolean(false)

    fun getItems() = listing.pagedListLiveData

    private val initObserver = Observer<PageStatus> {
        if (it == null) {
            onLoading(false)
        }
        when (it?.pageStatus) {
            PageState.LOADING -> onLoading(true)
            PageState.EMPTY -> {
                onLoading(false)
                onError(it.message?.code ?: 0, it.message?.message ?: "Nothing found :(")
            }
            PageState.SUCCESS -> onLoading(false)
            PageState.ERROR -> {
                onLoading(false)
                onError(it.message?.code ?: 0, it.message?.message ?: "Nothing found :(")
            }
        }
    }

    private fun observeInitProgress() {
        listing.initLoadLiveData.removeObserver(initObserver)
        listing.initLoadLiveData.observeForever(initObserver)
    }

    fun getPageProgress() = listing.pageLoadLiveData

    fun refreshItemsList() {
      //  listing.pagedListLiveData.value?.
    }

    fun retryPage() {
      //  (listing.pagedListLiveData.value? as BaseDataSource<*, *>).retryPagination()
    }

     fun fetchItems() {
        listing = setListing()
        observeInitProgress()
    }

    protected abstract fun setListing(): Listing<Item>

    override fun onCleared() {
        if (::listing.isInitialized)
            listing.initLoadLiveData.removeObserver(initObserver)
        super.onCleared()
    }
}