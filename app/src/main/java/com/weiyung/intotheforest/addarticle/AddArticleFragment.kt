package com.weiyung.intotheforest.addarticle

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.weiyung.intotheforest.NavigationDirections
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.databinding.FragmentAddarticleBinding
import com.weiyung.intotheforest.ext.addData
import com.weiyung.intotheforest.ext.getVmFactory
import com.weiyung.intotheforest.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Math.random
import java.util.*

class AddArticleFragment : Fragment() {
    private val viewModel by viewModels<AddArticleViewModel> {
        getVmFactory()
    }
    private lateinit var binding: FragmentAddarticleBinding

    companion object {
        const val PICTUREFROMGALLERY = 1001
        const val PICTUREFROMCAMERA = 1002
        const val REQUEST_EXTERNAL_STORAGE = 1003
        const val FIREBASE_PATH_ROUTE = "routeImg"
        const val FIREBASE_PATH_AVATAR = "avatar"
    }

    val storage = Firebase.storage

    //    private lateinit var viewModel: AddArticleViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddarticleBinding.inflate(inflater, container, false)
//        viewModel = ViewModelProvider(this).get(AddArticleViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.inputStartDate.setOnClickListener {
            setStartDate()
        }

        binding.inputEndDate.setOnClickListener {
            setEndDate()
        }

        binding.inputPhotoButton.setOnClickListener {
            permissionWritePhoto()
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICTUREFROMGALLERY)
        }

        binding.addPostButton.setOnClickListener {
            Log.i(TAG,"addFragment fun addData")
            Toast.makeText(this.requireContext(), R.string.post_success, Toast.LENGTH_LONG).show()
            this.findNavController().navigate(AddArticleFragmentDirections.actionAddArticleFragmentToPostSuccessFragment())
        }

        return binding.root
    }

    private fun permissionWritePhoto() {
        if (ContextCompat.checkSelfPermission(
                this.binding.root.context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_EXTERNAL_STORAGE
            )
        } else {
            getLocalImg()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocalImg()
                } else {
                    Toast.makeText(requireActivity(), R.string.nothing_happen, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i(TAG, "onActivityResult,resultCode : $resultCode")
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri = data?.data!!
                binding.pickImg1.setImageURI(uri)
                Log.i(TAG, "uri : $uri")
                val imagePath = getPathFromUri(uri)
//                val filePath: String = ImagePicker.getFilePath(data) ?: ""
                if (imagePath.isNotEmpty()) {
                    Log.i(TAG, "onActivityResult if imagePath.isNotEmpty()")
                    Toast.makeText(requireActivity(), imagePath, Toast.LENGTH_SHORT).show()
                    Glide.with(requireActivity()).load(imagePath).into(binding.pickImg1)
                    getLocalImg()

                    firebaseUpload(uri)
                    Log.i(TAG, "firebaseUpload: ")
                } else {
                    Toast.makeText(requireActivity(), R.string.load_img_fail1, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            ImagePicker.RESULT_ERROR -> Toast.makeText(
                requireContext(),
                ImagePicker.getError(data),
                Toast.LENGTH_SHORT
            ).show()
            else -> Toast.makeText(requireActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

//    private val startForProfileImageResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//            val resultCode = result.resultCode
//            val data = result.data
//
//            if (resultCode == Activity.RESULT_OK) {
//                //Image Uri will not be null for RESULT_OK
//                val fileUri = data?.data!!
//                Log.i(TAG, "fileUri : $fileUri")
//                val mProfileUri = fileUri
//                binding.pickImg2.setImageURI(fileUri)
//                Log.i(TAG, "startForProfileImageResult ")
//            } else if (resultCode == ImagePicker.RESULT_ERROR) {
//                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
//                    .show()
//            } else {
//                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
//            }
//        }

    private fun getPathFromUri(uri: Uri): String {
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
        val columnIndex: Int? = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA) ?: null
        cursor?.moveToFirst()
        val result: String = columnIndex?.let { cursor?.getString(it) } ?: ""
        cursor?.close()
        return result
    }

    fun getLocalImg() {
        ImagePicker.with(requireActivity())
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    fun firebaseUpload(uri: Uri) {
        coroutineScope.launch {
            val mStorageRef = FirebaseStorage.getInstance().reference
            val storageRef = storage.reference
            val imagesRef: StorageReference = storageRef.child(FIREBASE_PATH_ROUTE)
//                  val routeImageRef = storageRef.child("routeImg/image.jpg")
            val path = imagesRef.path
//            val file = Uri.fromFile(File())
            val metadata = StorageMetadata.Builder()
                .setContentDisposition("universe")
                .setContentType("image/jpg")
//                  .setContentType("image/png")
//                  .setContentType("image/jpeg")
                .build()
            val randomNumber = (0..999).random()
            val routesRef =
                mStorageRef.child("$FIREBASE_PATH_ROUTE/${UserManager.addUserInfo().id}_$randomNumber.jpg")
//                  val uploadTask = routesRef.putFile(file, metadata)
            val uploadTask = routesRef.putFile(uri)
            uploadTask.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    R.string.load_img_fail,
                    Toast.LENGTH_SHORT
                ).show()
//                  upload_info_text.text = exception.message
                Log.i(TAG, "addOnFailureListener: $it")
            }.addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    it.result.storage.downloadUrl.addOnCompleteListener {

                        val articleImage = it.result.toString()
                        viewModel.article.value?.mainImage = articleImage
                        Log.i(TAG, "the photoUri on firebasr storage is : $articleImage")
                    }
                }
                Toast.makeText(
                    requireContext(),
                    R.string.upload_success,
                    Toast.LENGTH_SHORT
                ).show()
//                  upload_info_text.setText(R.string.upload_success)
                Log.i(TAG, "addOnSuccessListener: $it")
            }
                .addOnProgressListener { taskSnapshot ->
                    val progress =
                        (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
                    binding.progressBar2.progress = progress
                    if (progress >= 100) {
                        binding.progressBar2.visibility = View.GONE
                    }
                }
        }
    }

    val c: Calendar = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    private fun setStartDate() {
        context?.let {
            DatePickerDialog(it, { _, year, month, day ->
                run {
                    binding.inputStartDate.setText(setDateFormat(year, month, day))
                }
            }, year, month, day).show()
        }
    }

    private fun setEndDate() {
        context?.let {
            DatePickerDialog(it, { _, year, month, day ->
                run {
                    binding.inputEndDate.setText(setDateFormat(year, month, day))
                }
            }, year, month, day).show()
        }
    }

    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        return "$year.${month + 1}.$day"
    }
}