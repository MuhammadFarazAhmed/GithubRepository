package com.app.base

import android.app.Activity
import android.os.Bundle

interface Navigator {
    fun startModule(
        activity: Activity,
        modules: Modules,
        bundle: Bundle? = null,
        startForResult: Int? = null,
        fromNotification: Boolean = false
    )

    enum class Modules {
        SPLASH, ONBOARDING, HOME, AUTH, SEARCH, CHAT, QRCODE
    }

    companion object {

        const val EXTRAS = "extra_bundle"
        const val ARG_GOTO = "GOTO"
        const val AUTH_ARG_FROM_HOME = "fromHome"
        const val AUTH_ARG_PHONE = "phone"
        const val AUTH_RESULT_PHONE_VERIFIED = "phoneVerified"
        const val AUTH_RESULT_PHONE_CHANGED = "phoneChanged"
        const val AUTH_RESULT_PASSWORD_CHANGED = "passwordChanged"
        const val AUTH_RESULT_EMAIL_VERIFIED = "emailVerified"
        const val AUTH_RESULT_EMAIL_CHANGED = "emailChanged"
        const val AUTH_RESULT_PROFILE_SET = "profileSet"
        const val AUTH_RESULT_LOGGEDIN = "isLoggedIn"
        const val PROFILE_ASAYDOER = "asAydoer"
        const val PROFILE_USER = "user"
        const val NOTIFICATION_TYPE = "notification_type"
        const val FROM_DEEP_LINK = "isFromDeepLink"
        const val RESOURCE_ID = "resource_id"
        const val BODY = "message"
        const val IS_FROM_BACKGROUND = "isFromBG"


        const val ONBOARDING_ARG_SHOWNFROM = "SHOWN_FROM"
        const val ONBOARDING_ARG_AUTH_GOTO = "authGOTO"
    }
}