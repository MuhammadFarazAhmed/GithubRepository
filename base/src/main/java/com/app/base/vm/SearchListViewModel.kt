package com.app.base.vm

import android.app.Application


abstract class SearchListViewModel<Item>constructor(application: Application) :
    SimpleListViewModel<Item>(application) {
    var tag: String = ""
    var selectedItem: Item?  = null
    init {
        isSaveButtonVisible.set(true)
        isBackButtonVisible.set(true)
    }

}
