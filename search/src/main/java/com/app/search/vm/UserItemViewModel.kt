package com.app.search.vm


import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.app.interfaces.models.User


class UserItemViewModel constructor(private val context: Context) : ViewModel() {
    lateinit var item: User
    val name = ObservableField("")
    val orgName = ObservableField("")
    val description = ObservableField("")
    val address = ObservableField("")
    
    
    fun setData(item: User) {
        this.item = item
        name.set(item.login)
//        orgName.set(item.company)
//        description.set(item)
//        address.set(item.email)
    }
    
}
