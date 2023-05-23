package com.example.tapshopping.utillz

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

    // Returning api response
    // wrapped in Resource class
    return withContext(Dispatchers.IO) {
        val response: Response<T>?
        try {

            // Here we are calling api lambda
            // function that will return response
            // wrapped in Retrofit's Response class
            response = apiToBeCalled()

            if (response.isSuccessful) {
                // In case of success response we
                // are returning Resource.Success object
                // by passing our data in it.
                Log.d("safeapi", "safeApiCall: success")
                Resource.success(data = response.body())

            } else {
                //In case of error response we
                // are returning the expected errorMessage
                // from the errorResponse object
                Log.d("safeeror", "safeApiCall: error")
                val errorBody = response.errorBody()?.string()
                val errorMessage = getAuthErrorResponse(errorBody).errorResponse.message
                Log.d("NetworkResourceBound", "safeApiCall: errorResponse -> $errorMessage ")
                Resource.error(message = errorMessage)
            }

        } catch (e: HttpException) {
            // Returning HttpException's message
            // wrapped in Resource.Error
            Resource.error(message = e.message ?: "An error occurred")
        } catch (e: IOException) {
            // Returning no internet message
            // wrapped in Resource.Error
            Resource.error("Please check your network connection")
        } catch (e: Exception) {
            // Returning 'Something went wrong' in case
            // of unknown error wrapped in Resource.Error
            Resource.error(message = e.message ?: "An error occurred")
        }
    }
}