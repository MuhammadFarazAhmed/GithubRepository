package com.app.interfaces.repository

import androidx.lifecycle.LiveData
import com.app.interfaces.models.*
import com.app.interfaces.models.common.LiveResponse
import com.app.interfaces.models.common.Message
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface AuthRepository {

    fun signIn(
       githubCode:String
    ): LiveData<LiveResponse<AccessToken>>
//
//    fun forgotPassword(
//        forgotpasswordInput: ForgotpasswordInput
//    ): LiveData<LiveResponse<Message>>
//
//    fun resetPassword(
//        resetPasswordInput: ResetPasswordInput
//    ): LiveData<LiveResponse<Message>>
//
//    fun validateToken(): LiveData<LiveResponse<Message>>
//
//    fun isLoggedIn(): LiveData<Boolean>
//
//    suspend fun checkIfCommonDataIsPresent(): Boolean
//
//    fun signOut(): LiveData<LiveResponse<Message>>
//
//    fun getUser(): LiveData<LiveResponse<UserOutput>>
//
//    fun getUserFromPrefs(): LiveData<UserOutput>
//
//    fun changePassword(changePasswordInput: ChangePasswordInput): LiveData<LiveResponse<Message>>
//
//    fun editProfile(updatedInput: UserUpdatedInput): LiveData<LiveResponse<UserOutput>>
//
//    suspend fun editProfileBg(updatedInput: UserUpdatedInput): UserOutput

}