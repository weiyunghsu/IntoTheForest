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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.databinding.FragmentAddarticleBinding
import com.weiyung.intotheforest.ext.getVmFactory
import com.weiyung.intotheforest.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class AddArticleFragment : Fragment() {
    private val viewModel by viewModels<AddArticleViewModel> { getVmFactory() }
    private lateinit var binding: FragmentAddarticleBinding

    companion object {
        const val PICTUREFROMGALLERY = 1001
        const val PICTUREFROMCAMERA = 1002
        const val REQUEST_EXTERNAL_STORAGE = 1003
        const val FIREBASE_PATH_ROUTE = "routeImg"
        const val FIREBASE_PATH_AVATAR = "avatar"
    }

    val storage = Firebase.storage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddarticleBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.chooseStartButton.setOnClickListener {
            setStartDate()
        }

        binding.chooseEndDateButton.setOnClickListener {
            setEndDate()
        }

        binding.inputPhotoButton.setOnClickListener {
            getLocalImg()
        }

        binding.addPostButton.setOnClickListener {
            viewModel.article.value?.let { it1 -> viewModel.addData(it1) }
            Toast.makeText(this.requireContext(), R.string.postSuccess, Toast.LENGTH_LONG).show()
        }
        binding.writePostLottie.repeatCount = -1
        binding.writePostLottie.playAnimation()
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
            viewModel.canUploadImage = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i(TAG, "onActivityResult,resultCode : $resultCode")
        when (resultCode) {
            Activity.RESULT_OK -> {

                data?.let {
                    val fileUri = data.data
                    Toast.makeText(requireActivity(), "已選取照片上傳中", Toast.LENGTH_SHORT).show()
                    binding.pickImg1.setImageURI(fileUri)
                    if (fileUri != null) {
                        firebaseUpload(fileUri)
                    }
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
             ImagePicker.with(this)
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
            val path = imagesRef.path
            val metadata = StorageMetadata.Builder()
                .setContentDisposition("universe")
                .setContentType("image/jpg")
                .build()
            val randomNumber = (0..999).random()
            val routesRef =
                mStorageRef.child("$FIREBASE_PATH_ROUTE/${UserManager.addUserInfo().id}_$randomNumber.jpg")
            val uploadTask = routesRef.putFile(uri)
            uploadTask.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    R.string.loadImgFail,
                    Toast.LENGTH_SHORT
                ).show()
                Log.i(TAG, "addOnFailureListener: $it")
            }.addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    it.result.storage.downloadUrl.addOnCompleteListener {
                        val articleImage = it.result.toString()
                        viewModel.article.value?.mainImage = articleImage
                        Log.i(TAG, "the photoUri on firebase storage is : $articleImage")
                    }
                }
                Toast.makeText(
                    requireContext(),
                    R.string.uploadSuccess,
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.isUploadSuccess = true
                Log.i(TAG, "addOnSuccessListener: $it")
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
                    binding.showStartDate.setText(setDateFormat(year, month, day))
                }
            }, year, month, day).show()
        }
    }

    private fun setEndDate() {
        context?.let {
            DatePickerDialog(it, { _, year, month, day ->
                run {
                    binding.showEndDate.setText(setDateFormat(year, month, day))
                }
            }, year, month, day).show()
        }
    }

    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        return "$year.${month + 1}.$day"
    }
}