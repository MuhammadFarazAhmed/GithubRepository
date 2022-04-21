package com.app.home.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import com.app.base.extensions.hideProgress
import com.app.base.extensions.showProgress
import com.app.base.ui.BaseFragment
import com.app.home.R
import com.app.home.adapter.RepoListingAdapter
import com.app.home.callback.UserFragmentCallback
import com.app.home.callback.UserfragmentViewCalllback
import com.app.home.databinding.FragmentUserBinding
import com.app.home.vm.UserViewModel
import com.app.interfaces.models.common.ApiStatus
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint class UserFragment : BaseFragment(), UserfragmentViewCalllback {
    
    private lateinit var binding: FragmentUserBinding
    private val vm: UserViewModel by viewModels()
    private var callback: UserFragmentCallback? = null
    
    private val viewPagerAdapter by lazy {
        RepoListingAdapter(this)
    }
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UserFragmentCallback) {
            callback = context
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
    }
    
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.callback = this
        binding.vm = vm
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initToolbar()
        initCollapsingToolbar()
        
        vm.getUserProfile().observe(viewLifecycleOwner) {
            onLoading(it.isLoading)
            when (it.status) {
                ApiStatus.LOADING -> {
                    showProgress("Logging in...")
                }
                ApiStatus.SUCCESS -> {
                    hideProgress()
                    binding.pager.adapter = viewPagerAdapter
                    TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
                        Log.d("TAG", "onViewCreated: $position,$tab")
                        when (position) {
                            0 -> tab.text = "Your Repository,s"
                            1 -> tab.text = "Starred Repository,s"
                        }
                    }.attach()
                }
                ApiStatus.ERROR -> {
                    hideProgress()
                    onErrorToast(it.error)
                }
            }
        }
        
    }
    
    private fun initToolbar() {
        binding.toolbar.inflateMenu(R.menu.main_menu)
        binding.toolbar.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
            Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                if (item.itemId == R.id.logout) {
                    vm.logout()
                    callback?.onLogout()
                }
                return false
            }
        })
    }
    
    private fun initCollapsingToolbar() {
        var isShow = true
        var scrollRange = -1
        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                binding.collapsingToolbarLayout.title = vm.user.value?.login
                isShow = true
            } else if (isShow) {
                binding.collapsingToolbarLayout.title =
                        " " //careful there should a space between double quote otherwise it wont work
                isShow = false
            }
        })
    }
    
    companion object {
        
        @JvmStatic fun newInstance() = UserFragment()
    }
    
    override fun onFollowersClicked() {
        takeIf { vm.user.value!!.followers != 0 }?.let { callback?.onFollowersClicked() }
    }
    
    override fun onFollowingsClicked() {
        takeIf { vm.user.value!!.following != 0 }?.let { callback?.onFollowingsClicked() }
    }
}