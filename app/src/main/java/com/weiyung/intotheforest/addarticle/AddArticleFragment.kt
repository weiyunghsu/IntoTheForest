package com.weiyung.intotheforest.addarticle

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddArticleFragment : Fragment() {
    private val viewModel by viewModels<AddArticleViewModel> { getVmFactory() }
    private lateinit var binding: FragmentAddarticleBinding

    companion object {
        const val FIREBASE_PATH_ROUTE = "routeImg"
    }

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

    fun getLocalImg() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()
    }

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    fun firebaseUpload(uri: Uri) {
        coroutineScope.launch {
            val storage = Firebase.storage
            val mStorageRef = FirebaseStorage.getInstance().reference
            val storageRef = storage.reference
            val imagesRef: StorageReference = storageRef.child(FIREBASE_PATH_ROUTE)
            imagesRef.path
            StorageMetadata.Builder()
                .setContentDisposition("universe")
                .setContentType("image/jpg")
                .build()
            val randomNumber = (0..999).random()
            val routesRef =
                mStorageRef.child(
                    "$FIREBASE_PATH_ROUTE/${UserManager.addUserInfo().id}_$randomNumber.jpg"
                )
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
                    binding.showStartDate.text = setDateFormat(year, month, day)
                }
            }, year, month, day).show()
        }
    }

    private fun setEndDate() {
        context?.let {
            DatePickerDialog(it, { _, year, month, day ->
                run {
                    binding.showEndDate.text = setDateFormat(year, month, day)
                }
            }, year, month, day).show()
        }
    }

    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        return "$year.${month + 1}.$day"
    }
}
        