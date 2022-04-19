package com.app.auth.callback

import com.app.base.callback.FragmentCallback

interface SigninCallback : FragmentCallback {

    fun onSignInSuccess()
    
    fun onGithubLoginComplete()
}