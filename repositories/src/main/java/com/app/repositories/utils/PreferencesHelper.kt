package com.app.repositories.utils

import android.content.SharedPreferences
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.interfaces.models.User
import com.google.gson.Gson

class PreferencesHelper
constructor(private val prefs: SharedPreferences, private val gson: Gson) {
    
    
    private val KEY_hasTermsAgreed = "termsAgreed"
    private val KEY_hasPolicyAgreed = "policyAgreed"
    private val KEY_fcmTokenSent = "fcmTokenSent"
    private val KEY_STOKEN = "stoken"
    private val KEY_USER_OBJ = "userObj"
    private val KEY_USER_TYPE = "userType"
    private val KEY_ISLOGGEDIN = "isLoggedIn"
    
    val authorization: ObservableField<String>
        get() = ObservableField(getAuthHeaders())
    
    val isLoggedIn: LiveData<Boolean>
        get() = MutableLiveData(isLoggedIn())
    
    val user: LiveData<User> = MutableLiveData(getUser())
    
    private fun isLoggedIn() = prefs.getBoolean(KEY_ISLOGGEDIN, false)
    
    fun saveAccessToken(accessToken: String?) {
        prefs.edit().putBoolean(KEY_ISLOGGEDIN, true).apply()
        prefs.edit().putString(KEY_STOKEN, accessToken).apply()
        authorization.set(accessToken)
        accessToken?.let { (isLoggedIn as MutableLiveData).value = (true) }
    }
    
    fun getAccessToken() = prefs.getString(KEY_STOKEN, "")
    
    fun saveUser(user: User) {
        (this.user as MutableLiveData).value = (user)
        prefs.edit().putString(KEY_USER_OBJ, gson.toJson(user)).apply()
    }
    
    private fun getUser(): User? {
        return gson.fromJson(prefs.getString(KEY_USER_OBJ, "{}"), User::class.java)
    }
    
    private fun getAuthHeaders() = prefs.getString(KEY_STOKEN, null)
    
    fun removeAuth() {
        prefs.edit().putBoolean(KEY_ISLOGGEDIN, false).apply()
        prefs.edit().remove(KEY_STOKEN).apply()
        prefs.edit().remove(KEY_USER_TYPE).apply()
        prefs.edit().remove(KEY_USER_OBJ).apply()
        authorization.set(null)
        (isLoggedIn as MutableLiveData).value = false
    }
}
