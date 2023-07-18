package com.example.tapshopping.utillz

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream

//convert uri to Base64
fun uriToBase64(context: Context, imageUri: Uri): String? {
    val contentResolver: ContentResolver = context.contentResolver
    val inputStream: InputStream = contentResolver.openInputStream(imageUri) ?: return null

    val bitmap: Bitmap = Bitmap.createBitmap(BitmapFactory.decodeStream(inputStream))

    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray: ByteArray = byteArrayOutputStream.toByteArray()

    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

// Convert Bitmap to Uri
 fun bitmapToUri( context: Context, bitmap: Bitmap): Uri? {
    val resolver: ContentResolver = context.contentResolver

    // Create a ContentValues object to hold the image metadata
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_${System.currentTimeMillis()}.jpg")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    // Insert the image metadata into the MediaStore
    val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    // Try to open an output stream for the image Uri
    imageUri?.let { uri ->
        resolver.openOutputStream(uri)?.use { outputStream ->
            // Compress the bitmap to the output stream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
        }
    }

    return imageUri
}