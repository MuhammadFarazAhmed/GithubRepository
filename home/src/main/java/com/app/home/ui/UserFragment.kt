package com.app.home.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.viewModels
import com.app.base.extensions.hideProgress
import com.app.base.extensions.showProgress
import com.app.base.ui.BaseFragment
import com.app.home.databinding.FragmentUserBinding
import com.app.home.vm.UserViewModel
import com.app.interfaces.models.common.ApiStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint class UserFragment : BaseFragment() {
    
    private lateinit var binding: FragmentUserBinding
    private val vm: UserViewModel by viewModels()
//    private var callback: SigninCallback? = null
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is SigninCallback) {
//            callback = context
//        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
    }
    
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
//        binding.callback = this
        binding.vm = vm
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getUserProfile().observe(viewLifecycleOwner) {
            onLoading(it.isLoading)
            when (it.status) {
                ApiStatus.LOADING -> {
                    showProgress("Logging in...")
                }
                ApiStatus.SUCCESS -> {
                    hideProgress()
                }
                ApiStatus.ERROR -> {
                    hideProgress()
                    onErrorToast(it.error)
                }
            }
        }
        
        vm.user.observe(viewLifecycleOwner) {
            Log.d("TAG", "onViewCreated: $it")
        }
        
//        binding.bLogout.setOnClickListener {
//            vm.logout()
//        }
    }
    
    companion object {
        
        @JvmStatic fun newInstance() = UserFragment().apply {
        
        }
    }
}