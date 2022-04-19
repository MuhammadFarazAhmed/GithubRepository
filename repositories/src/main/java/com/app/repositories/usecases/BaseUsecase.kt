package com.app.repositories.usecases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

open class BaseUsecase {

    private var job = Job()
    protected var scope: CoroutineScope = CoroutineScope(Dispatchers.Main + job)

    fun onDestroy() {
        scope.cancel()
    }
}