package com.app.interfaces.models.common


data class PageStatus(val pageStatus: PageState, val message: Message? = null)
enum class PageState {
    LOADING,
    EMPTY,
    SUCCESS,
    ERROR
}
