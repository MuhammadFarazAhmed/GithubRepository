package com.app.search.ui

import android.os.Bundle
import android.transition.TransitionManager
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.base.adapters.PagingLoadStateAdapter
import com.app.base.callback.RecyclerViewCallback
import com.app.base.extensions.closeKeyboard
import com.app.base.extensions.openKeyboard
import com.app.base.ui.BaseFragment
import com.app.interfaces.models.Repository
import com.app.interfaces.models.User
import com.app.search.adapter.UserListAdapter
import com.app.search.callback.SearchViewCallback
import com.app.search.databinding.SearchFragmentBinding
import com.app.search.vm.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint class SearchFragment : BaseFragment(), SearchViewCallback,
    RecyclerViewCallback<User> {
    
    private lateinit var binding: SearchFragmentBinding
    private val vm: SearchViewModel by viewModels()
    
    companion object {
        fun newInstance() = SearchFragment()
    }
    
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = vm
        binding.callback = this
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initSearch()
        vm.searchUsers()
    }
    
    private fun initList() {
        binding.rvSites.layoutManager = LinearLayoutManager(context)
        val adapter = UserListAdapter(this)
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.srlRefresh.isRefreshing = true
            } else {
                binding.srlRefresh.isRefreshing = false
                
                // getting the error
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    onErrorSimple(message = it.error.toString())
                }
            }
        }
        vm.users.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
        binding.rvSites.adapter =
                adapter.withLoadStateFooter(footer = PagingLoadStateAdapter { adapter.retry() })
        binding.srlRefresh.setOnRefreshListener {
            adapter.refresh()
        }
        vm.query.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) vm.searchUsers() else vm.setSearchArg("A")
        }
    }
    
    private fun initSearch() {
        var job: Job? = null
        val delay: Long = 500 // milliseconds
        binding.etSearch.addTextChangedListener {
            if (job?.isActive == true) {
                job?.cancel()
            }
            job = lifecycleScope.launch {
                delay(delay)
                vm.setSearchArg(it.toString())
            }
        }
    }
    
    override fun onSearchClicked() {
        binding.tvTitle.visibility = View.GONE
        binding.bSearch.visibility = View.GONE
        binding.bCancelSearch.visibility = View.VISIBLE
        binding.etSearch.visibility = View.VISIBLE
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
        binding.etSearch.postDelayed({
            binding.etSearch.requestFocusFromTouch()
            binding.etSearch.openKeyboard()
        }, 100)
        binding.srlRefresh.isEnabled = false
    }
    
    override fun onCancelSearchClicked() {
        binding.etSearch.closeKeyboard()
        binding.etSearch.setText("")
        binding.tvTitle.visibility = View.VISIBLE
        binding.bSearch.visibility = View.VISIBLE
        binding.bCancelSearch.visibility = View.GONE
        binding.etSearch.visibility = View.GONE
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
        binding.srlRefresh.isEnabled = true
    }
    
    override fun onListItemClicked(item: Repository, position: Int) {
    
    }
    
    override fun onListItemClicked(item: User, position: Int) {
    
    }
    
    
}