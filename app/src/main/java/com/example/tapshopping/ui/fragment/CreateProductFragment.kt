package com.example.tapshopping.ui.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.databinding.FragmentCreateProductBinding
import com.example.tapshopping.utillz.REQUEST_CODE_CAMERA
import com.example.tapshopping.utillz.REQUEST_CODE_GALLERY
import com.example.tapshopping.utillz.bitmapToUri
import com.example.tapshopping.utillz.uriToBase64


class CreateProductFragment : Fragment() {
    private lateinit var binding: FragmentCreateProductBinding
    private var imagePosition = -1
    private var categoryID: String? = ""
    private var imageUri4: Uri? = null
    private var imageUri1: Uri? = null
    private var imageUri2: Uri? = null
    private var imageUri3: Uri? = null
    private var imageString1: String? = null
    private var imageString2: String? = null
    private var imageString3: String? = null
    private var imageString4: String? = null

    private val imageActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val imageUri: Uri? = data?.data
                Log.d(":::::::ImagePos", imagePosition.toString())

                binding.submit.isVisible = data != null

                when (imagePosition) {
                    REQUEST_CODE_GALLERY -> {
                        imagePosition = if (imageUri != null) {
                            binding.uploadImage.setImageURI(imageUri)
                            imageUri1 = imageUri
                            -1
                        } else {
                            val imageBitmap = data?.extras?.get("data") as Bitmap
                            imageUri1 = bitmapToUri(requireContext(), imageBitmap)
                            binding.uploadImage.setImageURI(imageUri1)

                            -1
                        }
                    }

                    REQUEST_CODE_GALLERY + 1 -> {
                        imagePosition = if (imageUri != null) {
                            binding.uploadImage1.setImageURI(imageUri)
                            imageUri2 = imageUri
                            -1
                        } else {
                            val imageBitmap = data?.extras?.get("data") as Bitmap
                            imageUri2 = bitmapToUri(requireContext(), imageBitmap)
                            binding.uploadImage1.setImageURI(imageUri2)
                            -1
                        }
                    }

                    REQUEST_CODE_GALLERY + 2 -> {
                        imagePosition = if (imageUri != null) {
                            binding.uploadImage2.setImageURI(imageUri)
                            imageUri3 = imageUri
                            -1
                        } else {
                            val imageBitmap = data?.extras?.get("data") as Bitmap
                            imageUri3 = bitmapToUri(requireContext(), imageBitmap)
                            binding.uploadImage2.setImageURI(imageUri3)
                            -1
                        }
                    }

                    REQUEST_CODE_GALLERY + 3 -> {
                        imagePosition = if (imageUri != null) {
                            binding.uploadImage3.setImageURI(imageUri)
                            imageUri4 = imageUri
                            -1
                        } else {
                            val imageBitmap = data?.extras?.get("data") as Bitmap
                            imageUri4 = bitmapToUri(requireContext(), imageBitmap)
                            binding.uploadImage3.setImageURI(imageUri4)
                            -1
                        }
                    }

                }
                updateButtonState()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentCreateProductBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryID = CreateProductFragmentArgs.fromBundle(requireArguments()).categoryID

        Log.d(":::::::::::::::::catID", "onViewCreated: categoryId $categoryID ")

        updateButtonState()

        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            addImage.setOnClickListener {
                imagePosition = REQUEST_CODE_GALLERY
                chooseImage()
            }
            addImage1.setOnClickListener {
                imagePosition = REQUEST_CODE_GALLERY + 1
                chooseImage()
            }
            addImage2.setOnClickListener {
                imagePosition = REQUEST_CODE_GALLERY + 2
                chooseImage()
            }
            addImage3.setOnClickListener {
                imagePosition = REQUEST_CODE_GALLERY + 3
                chooseImage()
            }
            submit.setOnClickListener {
                setupUploadData()
                val uploadedData =
                    arrayOf(categoryID, imageString1, imageString2, imageString3, imageString4)
                findNavController().navigate(
                    CreateProductFragmentDirections.toCreateProductDetailsFragment(
                        uploadedData
                    )
                )
            }
        }
    }

    private fun chooseImage() {
        val optionsMenu = arrayOf<CharSequence>(
            "Take Photo",
            "Choose from Gallery",
            "Exit"
        ) // create a menuOption Array
        // create a dialog for showing the optionsMenu
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        // set the items in builder
        builder.setItems(
            optionsMenu
        ) { dialogInterface, i ->
            if (optionsMenu[i] == "Take Photo") {
                // Capture with camera
                checkCameraPermission()

            } else if (optionsMenu[i] == "Choose from Gallery") {
                // choose from  Gallery
                checkGalleryPermission()


            } else if (optionsMenu[i] == "Exit") {
                dialogInterface.dismiss()
            }
        }
        builder.setTitle("Select Action")
        builder.show()


    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE_CAMERA
            )
        }
    }

    private fun checkGalleryPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(
                requireActivity(),
                permission,
                REQUEST_CODE_GALLERY
            )

        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imageActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imageActivityResultLauncher.launch(intent)
    }

    private fun updateButtonState() {
        val isAnyImageUploaded =
            imageUri4 != null || imageUri1 != null || imageUri2 != null || imageUri3 != null
        binding.submit.isEnabled = isAnyImageUploaded
    }

    private fun setupUploadData() {
        if (imageUri1 != null) {
            imageString1 = uriToBase64(requireContext(), imageUri1!!)
        }

        if (imageUri2 != null) {
            imageString2 = uriToBase64(requireContext(), imageUri2!!)
        }

        if (imageUri3 != null) {
            imageString3 = uriToBase64(requireContext(), imageUri3!!)
        }

        if (imageUri4 != null) {
            imageString4 = uriToBase64(requireContext(), imageUri4!!)
        }
    }


}