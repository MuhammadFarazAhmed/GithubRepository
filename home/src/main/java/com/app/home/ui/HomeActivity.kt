package com.app.home.ui

import android.os.Bundle
import com.app.base.Navigator
import com.app.base.ui.BaseActivity
import com.app.home.R
import com.app.home.callback.UserFragmentCallback
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint class HomeActivity : BaseActivity(), UserFragmentCallback {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        
        addFragment(R.id.rlHomeContainer, UserFragment.newInstance())
        
    }
    
    
    override fun onBackPressed(tag: String?) {
        onBackPressed()
    }
    
    override fun onFollowersClicked() {
        navigator.startModule(this, Navigator.Modules.SEARCH)
    }
    
    override fun onFollowingsClicked() {
        navigator.startModule(this, Navigator.Modules.SEARCH)
    }
    
}