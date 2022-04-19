package com.app.home.ui

import android.os.Bundle
import com.app.base.ui.BaseActivity
import com.app.home.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint class HomeActivity : BaseActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        
        addFragment(R.id.rlHomeContainer, UserFragment.newInstance())
        
    }
    
    
    override fun onBackPressed(tag: String?) {
        onBackPressed()
    }
    
}