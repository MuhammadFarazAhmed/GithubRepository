package com.app.interfaces.models.common

data class LiveResponse<out T>(
    val callInfo: CallInfo,
    val data: T? = null
) {
    companion object {
        fun <T> loading() = LiveResponse<T>(callInfo = CallInfo(ApiStatus.LOADING, true))
        fun <T> success(data: T) =
            LiveResponse<T>(callInfo = CallInfo(ApiStatus.SUCCESS, false), data)

        fun <T> error(message: Message?) =
            LiveResponse<T>(callInfo = CallInfo(ApiStatus.ERROR, false, message))
    }
}

data class CallInfo(
    val status: ApiStatus,
    val isLoading: Boolean = false,
    val error: Message? = null
)

enum class ApiStatus {
    LOADING, SUCCESS, ERROR
}

