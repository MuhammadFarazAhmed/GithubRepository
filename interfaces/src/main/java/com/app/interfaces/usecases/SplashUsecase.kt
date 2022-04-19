package com.app.interfaces.usecases

import androidx.lifecycle.LiveData
import com.app.interfaces.models.common.DocumentUploadStatus
import com.app.interfaces.models.common.LiveResponse
import com.app.interfaces.models.common.Message

interface SplashUsecase {
    fun isLoggedIn(): LiveData<Boolean>
}