package com.weiyung.intotheforest.addarticle

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.RequestBody
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.User
import com.weiyung.intotheforest.database.source.remote.IntoTheForestRemoteDataSource.publish
import com.weiyung.intotheforest.databinding.FragmentAddarticleBinding
import com.weiyung.intotheforest.ext.getVmFactory
import java.io.File
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class AddArticleFragment : Fragment() {
    private val viewModel by viewModels<AddArticleViewModel> {
        getVmFactory(
            AddArticleFragmentArgs.fromBundle(requireArguments()).userKey
        )
    }
    private lateinit var binding: FragmentAddarticleBinding

    companion object {
        const val PICTUREFROMGALLERY = 1001
        const val PICTUREFROMCAMERA = 1002
    }

    val db = Firebase.firestore

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

        fun permissionPhoto() {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 888
            )
        }
        permissionPhoto()

        binding.inputStartDate.setOnClickListener {
            setStartDate()
        }

        binding.inputEndDate.setOnClickListener {
            setEndDate()
        }
//        binding.inputPhotoButton.setOnClickListener {
//            val gallery =
//                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            startActivityForResult(gallery, PICTUREFROMGALLERY)
//        }

        return binding.root
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