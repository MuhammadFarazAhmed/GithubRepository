package com.app.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.home.ui.RepoListingFragment


class RepoListingAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    
    override fun getItemCount(): Int = 2
    
    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        return RepoListingFragment.newInstance(position.toString())
    }
}