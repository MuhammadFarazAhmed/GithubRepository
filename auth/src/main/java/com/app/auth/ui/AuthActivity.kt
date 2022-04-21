package com.app.auth.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.app.auth.BuildConfig
import com.app.auth.R
import com.app.auth.callback.SigninCallback
import com.app.base.Navigator
import com.app.base.Navigator.Companion.EXTRAS
import com.app.base.ui.BaseActivity
import com.mlykotom.valifi.ValiFi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint class AuthActivity : BaseActivity(), SigninCallback {
    
    private val goto: Int by lazy {
        intent.getBundleExtra(EXTRAS)?.getInt(Navigator.ARG_GOTO, -1) ?: -1
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ValiFi.install(applicationContext)
        setContentView(R.layout.auth_activity)
        when (goto) {
            else -> replaceFragment(R.id.fAuthContainer, SignInFragment.newInstance())
        }
    }
    
    override fun onBackPressed(tag: String?) {
        if (closeKeyboard().not()) {
            onBackPressed()
        }
    }
    
    override fun onSignInSuccess() {
        val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/login/oauth/authorize?client_id=${BuildConfig.CLIENT_ID}&scope=repo,user&redirect_url=${BuildConfig.REDIRECT_URL}"))
        startActivity(intent)
    }
    
    override fun onGithubLoginComplete() {
        navigator.startModule(this, Navigator.Modules.HOME)
        finish()
    }
}

