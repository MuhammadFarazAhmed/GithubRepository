package com.app.auth.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.auth.BuildConfig
import com.app.auth.callback.SigninCallback
import com.app.auth.callback.SigninViewCallack
import com.app.auth.databinding.SigninFragmentBinding
import com.app.auth.vm.SignInViewModel
import com.app.base.extensions.hideProgress
import com.app.base.extensions.showProgress
import com.app.base.ui.BaseFragment
import com.app.interfaces.models.common.ApiStatus
import com.app.interfaces.models.common.DocumentUploadStatus
import com.app.interfaces.models.common.Message
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint class SignInFragment : BaseFragment(), SigninViewCallack {
    
    companion object {
        fun newInstance() = SignInFragment()
    }
    
    private lateinit var binding: SigninFragmentBinding
    private val vm: SignInViewModel by viewModels()
    private var callback: SigninCallback? = null
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SigninCallback) {
            callback = context
        }
    }
    
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = SigninFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.callback = this
        binding.vm = vm
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        vm.githubCode.observe(viewLifecycleOwner) { code ->
            takeIf { code.isNotEmpty() }.let {
                Log.d("TAG", "onViewCreated: $code")
                vm.signIn().observe(viewLifecycleOwner){
                    onLoading(it.isLoading)
                    when(it.status){
                        ApiStatus.LOADING -> {
                            showProgress("Logging in...")
                        }
                        ApiStatus.SUCCESS -> {
                            hideProgress()
                            callback?.onGithubLoginComplete()
                        }
                        ApiStatus.ERROR -> {
                            hideProgress()
                            onErrorToast(it.error)
                        }
                    }
                }
            }
        }
        
    }
    
    override fun onResume() {
        super.onResume()
        val uri = activity?.intent?.data
        if (uri != null && uri.toString().startsWith(BuildConfig.REDIRECT_URL)) {
            val code = uri.getQueryParameter("code")
            vm.githubCode.value = code
        }
    }
    
    override fun onSignInButtonClicked() {
        callback?.onSignInSuccess()
    }
    
    override fun onProgressDialogCancelled() {
    
    }
}

