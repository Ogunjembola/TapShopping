package com.example.tapshopping.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
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
import com.example.tapshopping.utillz.GlideLoader
import com.example.tapshopping.utillz.PICK_IMAGE_REQUEST_CODE
import com.example.tapshopping.utillz.READ_STORAGE_PERMISSION_CODE
import com.example.tapshopping.utillz.showImageChooser
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
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
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        // The uri of selected image from phone storage.
                        mSelectedImageFileUri = data.data!!

                        GlideLoader(requireContext()).loadUserPicture(
                            mSelectedImageFileUri!!, binding.ivUserPhoto
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            requireContext(),
                            resources.getString(R.string.image_selection_failed),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // A log is printed when user close or cancel the image selection.
            Log.e("Request Cancelled", "Image selection cancelled")
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
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun imageUploadSuccces(imageURL: String) {
        // hideProgressDialog()
        mUserProfileImageURL = imageURL
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