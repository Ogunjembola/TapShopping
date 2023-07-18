package com.example.tapshopping.ui.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.R
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.databinding.FragmentUserProfileBinding
import com.example.tapshopping.utillz.CAMERA_REQUEST_CODE
import com.example.tapshopping.utillz.GlideLoader
import com.example.tapshopping.utillz.PERMISSION_REQUEST_CODE
import com.example.tapshopping.utillz.PICK_IMAGE_REQUEST_CODE
import com.example.tapshopping.utillz.READ_STORAGE_PERMISSION_CODE
import com.example.tapshopping.utillz.showImageChooser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class
UserProfileFragment : Fragment(), View.OnClickListener {

    @Inject
    lateinit var dataStoreManager: DataStoreManager
    private lateinit var binding: FragmentUserProfileBinding
    private var mSelectedImageFileUri: Uri? = null
    private var mUserProfileImageURL: String = " "
    private var isEditable: Boolean = false
    lateinit var currentPhotoPath: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarUserProfileActivity.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.ivUserPhoto.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)

        setUpProfile()


        binding.btnSave.setOnClickListener {
            activateEditText()
            isEditable = if (isEditable){
                binding.etFullName.isEnabled = true
                binding.btnSave.setText(R.string.btn_lbl_save)
                !isEditable
            }else{
                binding.btnSave.setText(R.string.update)
                true
            }
        }


    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.iv_user_photo -> {
                    chooseImage(requireActivity())


                }

                R.id.btn_save -> {
                    binding.btnSave.setText(R.string.loading)
                    if (mSelectedImageFileUri != null) {
                        //Upload image to api
                    } else {
                        Toast.makeText(context, "Please Select an Image", Toast.LENGTH_LONG).show()
                    }
                }


            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageChooser(this@UserProfileFragment)

            }
        } else {
            //Displaying another toast if permission is not granted
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.read_storage_permission_denied),
                Toast.LENGTH_LONG
            ).show()
        }
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val thumbnail = data?.extras?.get("data") as? Bitmap
                    if (thumbnail != null) {
                        binding.ivUserPhoto.setImageBitmap(thumbnail)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to load image from camera.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                PICK_IMAGE_REQUEST_CODE -> {
                    mSelectedImageFileUri = data?.data
                    if (mSelectedImageFileUri != null) {
                        // Load and display the selected image
                        // ...
                        GlideLoader(requireContext()).loadUserPicture(
                            mSelectedImageFileUri!!, binding.ivUserPhoto
                        )

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to load image from gallery.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            // A log is printed when user close or cancel the image selection.
            Log.e("Request Cancelled", "Image selection cancelled")
        }


        fun imageUploadSuccces(imageURL: String) {

            mUserProfileImageURL = imageURL

        }
    }

    private fun chooseImage(context: Context) {
        val optionsMenu = arrayOf<CharSequence>(
            "Take Photo",
            "Choose from Gallery",
            "Exit"
        ) // create a menuOption Array
        // create a dialog for showing the optionsMenu
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        // set the items in builder
        builder.setItems(optionsMenu,
            DialogInterface.OnClickListener { dialogInterface, i ->
                if (optionsMenu[i] == "Take Photo") {
                    // Open the camera and get the photo
                    cameraPermission()

                } else if (optionsMenu[i] == "Choose from Gallery") {
                    // choose from  external storage
                    galleryPermission()


                } else if (optionsMenu[i] == "Exit") {
                    dialogInterface.dismiss()
                }
            })
        builder.show()


    }

    private fun galleryPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            showImageChooser(this@UserProfileFragment)
        } else {
            /*Requests permissions to be granted to this application. These permissions
             must be requested in your manifest, they should not be granted to your app,
             and they should have protection level*/
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_STORAGE_PERMISSION_CODE
            )

        }
    }

    private fun cameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CODE
            )
        }


        fun imageUploadSuccces(imageURL: String) {
            // hideProgressDialog()
            mUserProfileImageURL = imageURL

        }
    }

    private fun setUpProfile(){
        binding.apply {
            etFullName.setText(dataStoreManager.fullName)
            etEmail.setText(dataStoreManager.email)
            etUserName.setText(dataStoreManager.userName)
        }
    }

    private fun activateEditText(){
            binding.etFullName.isClickable = true
            binding.etFullName.isFocusable = true
            binding.etFullName.isEnabled = true

            binding.etUserName.isClickable = true
            binding.etUserName.isFocusable = true
            binding.etUserName.isEnabled = true

            binding.etFullName.isClickable = true
            binding.etFullName.isFocusable = true
            binding.etFullName.isEnabled = true
    }

}