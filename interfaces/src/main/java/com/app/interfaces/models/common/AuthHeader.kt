package com.app.interfaces.models.common

data class AuthHeader(var sid: Int? = null, var stoken: String? = null) {
    fun checkNull(): Boolean {
        return sid == null || stoken == null
    }
}
