package com.example.tapshopping.utillz

import android.content.Intent
import android.provider.MediaStore
import com.example.tapshopping.ui.fragment.UserProfileFragment

const val SHOPPING_DATA_STORE_NAME ="shoppingDataStoreName"

const val BASE_URL = "https://lab-ecom-api-4c56x6bkca-uc.a.run.app/api/v1/"
const val API_CONNECT_TIMEOUT = 50L
const val API_READ_TIMEOUT = 50L
const val API_WRITE_TIMEOUT = 50L

const val SUCCESSFULLY_LOGGED_IN = "LOGIN SUCCESSFUL"
//A unique code for asking the Read Storage Permission using this we will be check and identify in the method onRequestPermissionsResult in the Base Activity.
const val READ_STORAGE_PERMISSION_CODE = 2

// A unique code of image selection from Phone Storage.
const val PICK_IMAGE_REQUEST_CODE = 2
fun showImageChooser(activity: UserProfileFragment) {
    // An intent for launching the image selection of phone storage.
    val galleryIntent = Intent(
        Intent.ACTION_PICK,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    )
    // Launches the image selection of phone storage using the constant code.
    activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
}
