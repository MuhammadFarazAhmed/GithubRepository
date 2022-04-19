package com.app.base.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.base.adapters.BasePagedListAdapter
import com.app.base.callback.PagedListViewCallback
import com.app.base.databinding.PagedListFragmentBinding
import com.app.base.vm.PagedViewModel

abstract class PagedListFragment<Item : Any, VM : PagedViewModel<Item>, adapter : BasePagedListAdapter<Item>> :
    Fragment(),
    PagedListViewCallback<Item> {

    protected lateinit var binding: PagedListFragmentBinding

    protected lateinit var vm: VM
    protected lateinit var adapter: adapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PagedListFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.callback = this
        binding.vm = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        binding.rvSelectableList.layoutManager = layoutManager
        binding.rvSelectableList.adapter = adapter
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (positionStart == 0) {
                    layoutManager.scrollToPosition(0)
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm.fetchItems()
        vm.getItems().observe(viewLifecycleOwner, {
            binding.srlRefresh.isRefreshing = false
            adapter.submitData(lifecycle, it)
        })
        vm.getPageProgress().observe(viewLifecycleOwner, Observer {
            adapter.changePageState(it)
        })
        binding.srlRefresh.setOnRefreshListener {
            onRetryButtonClicked()
        }
    }

    override fun onPageReload() {
        vm.retryPage()
    }

    override fun onRetryButtonClicked() {
        vm.refreshItemsList()
    }
}
