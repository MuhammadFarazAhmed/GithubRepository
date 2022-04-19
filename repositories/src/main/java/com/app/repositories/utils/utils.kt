package com.app.repositories.utils

import androidx.lifecycle.MutableLiveData
import androidx.work.WorkInfo
import com.app.interfaces.models.common.DocumentUploadStatus

fun setState(state: WorkInfo.State, status: MutableLiveData<DocumentUploadStatus>) {
    if (state == WorkInfo.State.ENQUEUED || state == WorkInfo.State.RUNNING) {
        //  is LOADING
        if (status.value != DocumentUploadStatus.LOADING)
            status.value = DocumentUploadStatus.LOADING
    } else if (state == WorkInfo.State.BLOCKED) {
        //  it will run when requirements are met
        if (status.value != DocumentUploadStatus.LOADING)
            status.value = DocumentUploadStatus.LOADING
    } else if (state == WorkInfo.State.FAILED) {
        if (status.value != DocumentUploadStatus.ERROR)
            status.value = DocumentUploadStatus.ERROR
    } else if (state == WorkInfo.State.CANCELLED) {
        if (status.value != DocumentUploadStatus.CANCELLED)
            status.value = DocumentUploadStatus.CANCELLED
    } else {
        if (status.value != DocumentUploadStatus.SUCCESS)
            status.value = DocumentUploadStatus.SUCCESS
    }
}