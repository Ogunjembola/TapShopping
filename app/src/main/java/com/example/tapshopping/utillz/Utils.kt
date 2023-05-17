package com.example.tapshopping.utillz

import android.app.Dialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

const val SHOPPING_DATA_STORE_NAME ="shoppingDataStoreName"
private var doubleBackToExitPressedOnce =false
private lateinit var mProgressDialog: Dialog
const val API_CONNECT_TIMEOUT = 50L
const val API_READ_TIMEOUT = 50L
const val API_WRITE_TIMEOUT = 50L

suspend fun <T> sendRequest(action: suspend () -> T): Resource<T> =
    try {
        Resource.success(action())
    } catch (e: Exception) {

        Resource.error("Connection error. Try again")
    }
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
                Resource.success(data = response.body())
            } else {
                Resource.error(message = "An error occurred")
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
            Resource.error(message = "An error occurred")
        }
    }
}

/*fun showErrorSnackBar(message: String, errorMessage: Boolean) {
    val contextView = findViewById<View>
    val snackBar =
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
    val snackBarView = snackBar.view


 */

    /* Set the screen content  from layout resources.
    the resource will be inflated, adding  all top- level views  to the screen.
     */
  /*  mProgressDialog.setContentView(R.layout.dialog_progress)


    mProgressDialog.setCancelable(false)
    mProgressDialog.setCanceledOnTouchOutside(false)

    //Start the dialog and display it on screen
    mProgressDialog.show()

}*/

fun hideProgressDialog() {
    // Dissmiss dialog removing it from the screen
    mProgressDialog.dismiss()
}
/*fun doubleBackExit(){
    if (doubleBackToExitPressedOnce){
        super.onBackPressed()
        return
    }
    this.doubleBackToExitPressedOnce = true
    Toast.makeText(
        this,resources.getString(R.string.please_click_back_again_to_exit),
        Toast.LENGTH_SHORT
    ).show()
    @Suppress("DEPRECATION")
    Handler().postDelayed({doubleBackToExitPressedOnce= false},2000)
}*/
