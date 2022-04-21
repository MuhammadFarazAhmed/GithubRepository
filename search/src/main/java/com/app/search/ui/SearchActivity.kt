package com.app.search.ui

import android.os.Bundle
import com.app.base.ui.BaseActivity
import com.app.search.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint class SearchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        
        addFragment(R.id.rlSearchContainer, SearchFragment.newInstance())
    }
    
    override fun onBackPressed(tag: String?) {
    
    }
}