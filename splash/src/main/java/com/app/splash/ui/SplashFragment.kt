package com.app.splash.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.base.Navigator
import com.app.base.ui.BaseFragment
import com.app.interfaces.models.common.ApiStatus
import com.app.interfaces.models.common.DocumentUploadStatus
import com.app.interfaces.models.common.Message
import com.app.splash.callback.SplashCallback
import com.app.splash.callback.SplashViewCallback
import com.app.splash.databinding.SplashFragmentBinding
import com.app.splash.vm.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment(), SplashViewCallback {

    companion object {
        private const val LOGOUT = "logout"
        fun newInstance() = SplashFragment()
    }

    private lateinit var binding: SplashFragmentBinding
    private val vm: SplashViewModel by viewModels()
    private var callback: SplashCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SplashFragmentBinding.inflate(inflater, container, false)
        binding.vm = vm
        binding.callback = this
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SplashCallback) {
            callback = context
        } else throw RuntimeException("SplashCallback not implemented")
    }
    
    override fun onRetryButtonClicked() {
    
    }
    
    
}