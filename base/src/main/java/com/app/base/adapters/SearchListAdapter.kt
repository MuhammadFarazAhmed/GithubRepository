package com.app.base.adapters

import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.app.base.callback.RecyclerViewCallback
import com.app.base.callback.RecyclerViewItemCallback


abstract class SearchListAdapter<Item, bind : ViewDataBinding, VH : SearchListAdapter<Item, bind, VH>.SearchViewHolder<bind>> constructor(
    val isMutiSelect: Boolean,
    private val callback: RecyclerViewCallback<Item>? = null,
    diffCallback: DiffUtil.ItemCallback<Item>
) : SimpleListAdapter<Item, bind, VH>(callback, diffCallback), Filterable {


    val allList = mutableListOf<Item>()
    protected val selectedItemsList = mutableListOf<Item>()

    //protected var singleSelectedPos: Int = -1
    // protected val selectedItems = mutableListOf<Item>()

    abstract fun isItemFound(item: Item, query: String): Boolean

    fun selectedItems(items: List<Item>) {
        currentList.forEachIndexed { _, currentItem ->
            items.forEach {
                if (currentItem?.equals(it) == true) {
                    setItemSelected(it)
                }
            }
        }
    }

    fun setItemSelected(item: Item) {
        val isSelected = selectedItemsList.indexOf(item) != -1
        if (isMutiSelect.not()) {
            unselectAll()
        }
        if (isSelected) {
            selectedItemsList.remove(item)
        } else {
            // selectedPositions.put(selectedPosition, true)
            selectedItemsList.add(item)
        }
        notifyItemChanged(currentList.indexOf(item))
    }

    private fun unselectAll() {
        selectedItemsList.clear()
        notifyDataSetChanged()
    }

    fun submitListFromUi(list: List<Item>) {
        allList.clear()
        allList.addAll(list)
        submitList(list)
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                val filteredList = mutableListOf<Item>()
                if (charString.isEmpty()) {
                    filteredList.addAll(allList)
                } else {
                    for (row in allList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (isItemFound(row, charString)) {
                            filteredList.add(row)
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                filterResults.count = filteredList.size
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                submitList((filterResults.values as List<Item>?))
                // refresh the list with filtered data
                //  notifyDataSetChanged()
            }
        }
    }

    //fun isItemSelected(position: Int) = selectedItemsList.get(position, false)
    fun isItemSelected(item: Item) = selectedItemsList.indexOf(item) != -1

    fun getSelectedItems(): List<Item> {
       /* val selectedItems = mutableListOf<Item>()
        selectedPositions.forEach { key, _ ->
            selectedItems.add(getItem(key))
        }*/
        return selectedItemsList
    }

    abstract inner class SearchViewHolder<bind : ViewDataBinding>(private val binding: bind) :
        SimpleViewHolder<bind>(binding),
        RecyclerViewItemCallback<Item> {

        override fun onListItemClicked(item: Item) {
            setItemSelected(item)
            super.onListItemClicked(item)
        }
    }
}


