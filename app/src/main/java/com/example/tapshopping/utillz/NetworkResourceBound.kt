package com.example.tapshopping.utillz

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend inline fun <reified T> safeApiCall(crossinline apiToBeCalled: suspend () -> Response<T>): Resource<T> {

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
                Resource.Success(data = response.body())
            } else {
                val error = response.errorBody()?.string()


                val message = getAuthErrorResponse(error).errorResponse.message
                Log.d("NetworkResourceBound", "safeApiCall: errorResponse -> $message ")
                Resource.Error( message = message )
            }

        } catch (e: HttpException) {
            // Returning HttpException's message
            // wrapped in Resource.Error
            Resource.Error(message = e.message ?: "An error occurred")
        } catch (e: IOException) {
            // Returning no internet message
            // wrapped in Resource.Error
            Resource.Error(message = "Please check your network connection")
        } catch (e: Exception) {
            // Returning 'Something went wrong' in case
            // of unknown error wrapped in Resource.Error
            Resource.Error(message = "An error occurred")
        }
    }
}