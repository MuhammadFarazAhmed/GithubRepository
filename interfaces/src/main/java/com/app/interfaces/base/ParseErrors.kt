package com.app.interfaces.base

import android.util.Log
import com.app.interfaces.models.common.Message
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class ParseErrors() {


    fun parseException(throwable: Throwable): Message {
        val errorMessage = Message()
        throwable.printStackTrace()
        errorMessage.code = 900
        if (throwable is UnknownHostException) {
            errorMessage.code = 901
            errorMessage.message = "Unable to connect to internet"
        } else if (throwable is ConnectException || throwable is SocketTimeoutException) {
            errorMessage.code = 902
            errorMessage.message = "Unable to connect, Please retry"
        }
        if (errorMessage.message.isEmpty() || errorMessage.message.isBlank()) {
            errorMessage.message = throwable.message ?: "Unkown error"
        }
        return errorMessage
    }

    suspend fun parseError(throwable: Throwable): Message {
        var errorMessage = Message()
        throwable.printStackTrace()
        errorMessage.code = 900
        if (throwable is UnknownHostException) {
            errorMessage.code = 901
            errorMessage.message = "Unable to connect to internet"
        } else if (throwable is ConnectException || throwable is SocketTimeoutException) {
            errorMessage.code = 902
            errorMessage.message = "Unable to connect, Please retry"
        } else if (throwable is HttpException) {
            errorMessage = parseServerErrors(throwable)
        }
        if (errorMessage.message.isEmpty() || errorMessage.message.isBlank()) {
            errorMessage.message = throwable.message ?: "Unkown error"
        }
        return errorMessage
    }

    suspend fun parseServerErrors(httpException: HttpException): Message {
        return withContext(Dispatchers.IO) {
            val errorObject = Message()
            errorObject.code = httpException.code()
            try {
                val body =
                    httpException.response()?.errorBody()?.string() ?: httpException.message()
                if (body.isNotEmpty() && !body.contains("</html>")) {
                    val o = JsonParser.parseString(body).asJsonObject
                    val ob = o.asJsonObject.get("message")
                    errorObject.message = if (ob.isJsonArray) {
                        ob.asJsonArray[0].asString
                    } else {
                        ob.asString
                    }
                }
                if (errorObject.message.isEmpty() || errorObject.message.isBlank())
                    errorObject.message = httpException.message()
            } catch (e: Exception) {
                Log.e("error", e.message, e)
                errorObject.message = httpException.message()
            }
            return@withContext errorObject
        }
    }


}
