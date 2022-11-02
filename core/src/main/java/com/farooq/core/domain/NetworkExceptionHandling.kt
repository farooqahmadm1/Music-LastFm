package com.farooq.core.domain


import com.farooq.core.utils.Logger
import com.google.android.material.R
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class NetworkExceptionHandling @Inject constructor(val logger: Logger) {

    companion object {
        private const val TAG = "NetworkException"
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }

    fun <T> execute(e: Exception): Resource<T> {
        logger.log(e.toString())
        return when (e) {
            is HttpException -> {
                when {
                    e.code() == 401 -> {
                        Resource.Error(UIComponent.Dialog("Session Expired", "your token has been expired please logout"), ErrorType.SESSION_EXPIRED)
                    }
                    else -> getErrorMessage(e.response()?.errorBody())
                }
            }
            is ConnectException -> {
                Resource.Error(UIComponent.None(e.localizedMessage?.toString() ?: "Not Able to Connect to Host"), ErrorType.NETWORK)
            }
            is SocketTimeoutException -> {
                Resource.Error(UIComponent.Dialog("Timeout", "Please check your internet connection is down or poor."), ErrorType.TIMEOUT)
            }
            is IOException -> Resource.Error(UIComponent.None("Please check your internet connection"), ErrorType.NETWORK)
            else -> Resource.Error(UIComponent.None("Something went wrong"), ErrorType.UNKNOWN)
        }
    }

    private fun <T> getErrorMessage(responseBody: ResponseBody?): Resource<T> {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            if (jsonObject.has(ERROR_KEY) && map[jsonObject.getInt(ERROR_KEY)] != null) {
                val list = map[jsonObject.getInt(ERROR_KEY)]!!.split("-")
                Resource.Error(UIComponent.Dialog(list[0], list[1]), ErrorType.NETWORK)
            } else {
                val message = when {
                    jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                    else -> "Something wrong happened"
                }
                Resource.Error(UIComponent.None(if (message.length > 400) "Please Try Again" else message), ErrorType.NETWORK)
            }
        } catch (e: Exception) {
            Resource.Error(UIComponent.None("Something wrong happened"), ErrorType.NETWORK)
        }
    }


    fun handleMessage(e: Exception): String {
        logger.log(e.toString())
        return when (e) {
            is HttpException -> {
                when {
                    e.code() == 401 -> "your token has been expired please logout"
                    else -> getMessage(e.response()?.errorBody())
                }
            }
            is ConnectException -> e.localizedMessage?.toString() ?: "Not Able to Connect to Host"
            is SocketTimeoutException -> "Please check your internet connection is down or poor."
            is IOException -> "Please Check your internet connection"
            else -> "Something went wrong"
        }
    }

    private fun getMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            if (jsonObject.has(ERROR_KEY) && map[jsonObject.getInt(ERROR_KEY)] != null) {
                return map[jsonObject.getInt(ERROR_KEY)]!!
            } else {
                val message = when {
                    jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                    else -> "Something wrong happened"
                }
                return if (message.length > 400) "Please Try Again" else message
            }
        } catch (e: Exception) {
            return "Something wrong happened"
        }
    }


    val map = mapOf<Int, String>(
        Pair(2, "Invalid service - This service does not exist"),
        Pair(3, "Invalid Method - No method with that name in this package"),
        Pair(4, "Authentication Failed - You do not have permissions to access the service"),
        Pair(5, "Invalid format - This service doesn't exist in that format"),
        Pair(6, "Invalid parameters - Your request is missing a required parameter"),
        Pair(7, "Please try again - Invalid resource specified"),
        Pair(8, "Operation failed - Something else went wrong"),
        Pair(9, "Invalid session key - Please re-authenticate"),
        Pair(10, "Invalid API key - You must be granted a valid key by last.fm"),
        Pair(11, "Service Offline - This service is temporarily offline. Try again later."),
        Pair(13, "Please try again - Invalid method signature supplied"),
        Pair(16, "Please try again - There was a temporary error processing your request."),
        Pair(26, "Suspended API key - Access for your account has been suspended, please contact Last.fm"),
        Pair(29, "Rate limit exceeded - Your IP has made too many requests in a short period"),
    )
}