package com.app.repositories.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.app.interfaces.base.ParseErrors
import com.app.interfaces.models.common.LiveResponse
import kotlinx.coroutines.Dispatchers

open class BaseRepository(private val parseErrors: ParseErrors) {


    fun <T, M> transforms(
        apiCall: LiveData<LiveResponse<T>>,
        transforms: (LiveResponse<T>) -> LiveResponse<M>
    ) = Transformations.map(apiCall) {
        try {
            transforms(it)
        } catch (e: Exception) {
            LiveResponse.error(parseErrors.parseException(e))
        }
    }


    fun <T> callApi(apiCall: suspend () -> T) = liveData(Dispatchers.IO) {
        emit(LiveResponse.loading())
        try {
            emit(LiveResponse.success(apiCall()))
        } catch (e: Exception) {
            emit(LiveResponse.error<T>(handleException(e)))
        }

    }


    private suspend fun handleException(e: Exception) = parseErrors.parseError(e)
}