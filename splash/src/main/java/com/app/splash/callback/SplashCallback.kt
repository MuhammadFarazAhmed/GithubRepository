package com.app.splash.callback

import com.app.base.Navigator


interface SplashCallback {

    fun onStartActivity(module: Navigator.Modules, goto: Int? = null)
}

