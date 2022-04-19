package com.app.splash.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.app.base.Navigator
import com.app.base.Navigator.Companion.ARG_GOTO
import com.app.base.Navigator.Companion.FROM_DEEP_LINK
import com.app.base.Navigator.Companion.NOTIFICATION_TYPE
import com.app.base.Navigator.Companion.RESOURCE_ID
import com.app.base.ui.BaseActivity
import com.app.splash.callback.SplashCallback
import dagger.hilt.android.AndroidEntryPoint
import com.app.splash.R
import com.app.splash.databinding.ActivitySplashBinding

@AndroidEntryPoint
class SplashActivity : BaseActivity(), SplashCallback {

    override fun onBackPressed(tag: String?) {
        onBackPressed()
    }

    private lateinit var binding: ActivitySplashBinding

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        hideSystemUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_splash
        )

        addFragment(R.id.flSplash, SplashFragment.newInstance())
    }


    override fun onStartActivity(module: Navigator.Modules, goto: Int?) {
        val bundle = intent.extras ?: Bundle()
        bundle.putBoolean(Navigator.IS_FROM_BACKGROUND, true)
        if (goto != null && module == Navigator.Modules.AUTH) {
            bundle.putInt(ARG_GOTO, goto)
            navigator.startModule(this, module, bundle)
        } else if (goto != null && module == Navigator.Modules.ONBOARDING) {
            bundle.putInt(Navigator.ONBOARDING_ARG_AUTH_GOTO, goto)
            navigator.startModule(this, module, bundle)
        } else {
            navigator.startModule(this, module, bundle)
        }
        finish()
    }

}
