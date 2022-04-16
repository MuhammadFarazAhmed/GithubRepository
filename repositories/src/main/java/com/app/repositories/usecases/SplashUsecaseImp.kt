package com.app.repositories.usecases

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.app.interfaces.Const
import com.app.interfaces.models.common.DocumentUploadStatus
import com.app.interfaces.repository.AuthRepository
import com.app.interfaces.usecases.SplashUsecase
import com.app.repositories.utils.setState
import kotlinx.coroutines.launch

class SplashUsecaseImp constructor(
    private val authRepository: AuthRepository
) : BaseUsecase(), SplashUsecase {


}