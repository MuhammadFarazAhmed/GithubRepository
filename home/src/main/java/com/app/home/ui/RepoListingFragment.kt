package com.app.home.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.base.adapters.PagingLoadStateAdapter
import com.app.base.callback.RecyclerViewCallback
import com.app.base.ui.BaseFragment
import com.app.home.adapter.RepoListAdapter
import com.app.home.databinding.FragmentRepoListingBinding
import com.app.home.vm.RepoListViewModel
import com.app.interfaces.models.Repository
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "repo_type" //0 -> all 1 -> starred


@AndroidEntryPoint class RepoListingFragment : BaseFragment(), RecyclerViewCallback<Repository> {
    private var type: String? = null
    
    private val vm: RepoListViewModel by viewModels()
    private lateinit var binding: FragmentRepoListingBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(ARG_PARAM1)
        }
    }
    
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentRepoListingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
//        binding.callback = this
        binding.vm = vm
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initList()
        
        vm.hasUser().observe(viewLifecycleOwner) {
            if (it) loadRepos()
        }
    }
    
    private fun loadRepos() {
        when (type) {
            "0" -> vm.getUserRepos()
            "1" -> vm.getUserStarredRepos()
        }
    }
    
    private fun initList() {
        binding.rvRepoList.layoutManager = LinearLayoutManager(context)
        val adapter = RepoListAdapter(this)
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
        
        when (type) {
            "0" -> vm.repos.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }
            "1" -> vm.starredRepos.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }
        }
        
        binding.rvRepoList.adapter =
                adapter.withLoadStateFooter(footer = PagingLoadStateAdapter { adapter.retry() })
        binding.srlRefresh.setOnRefreshListener {
            adapter.refresh()
        }
        
    }
    
    companion object {
        @JvmStatic fun newInstance(type: String) = RepoListingFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, type)
            }
        }
    }
    
    override fun onListItemClicked(item: Repository, position: Int) {
    
    }
}