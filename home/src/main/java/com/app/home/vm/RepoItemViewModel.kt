package com.app.home.vm


import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.app.interfaces.models.Repository


class RepoItemViewModel constructor(private val context: Context) : ViewModel() {
    lateinit var item: Repository
    val name = ObservableField("")
    


    fun setData(item: Repository) {
        this.item = item
        name.set(item.name)
    }

}
