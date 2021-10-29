package com.weiyung.intotheforest.addarticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.weiyung.intotheforest.database.Article
import com.weiyung.intotheforest.database.User
import com.weiyung.intotheforest.database.source.remote.IntoTheForestRemoteDataSource.publish
import com.weiyung.intotheforest.databinding.FragmentAddarticleBinding
import com.weiyung.intotheforest.ext.getVmFactory
import java.time.Instant
import java.time.format.DateTimeFormatter

class AddArticleFragment : Fragment() {
    private val viewModel by viewModels<AddArticleViewModel> {getVmFactory(
            AddArticleFragmentArgs.fromBundle(requireArguments()).userKey)
    }
    private lateinit var binding: FragmentAddarticleBinding
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

        val newID = db.collection("data").document().id
        val createTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now()).toLong()
        val editStartDate = binding.inputStartDate.text.toString()
        val editEndDate = binding.inputEndDate.text.toString()
        val editUser = User("3748","Amy","aaa@gmail.com","3939889","xxxUri")
        val editTitle = binding.inputTitle.text.toString()
        val editStory = binding.inputStory.text.toString()
        val editArticle = Article(
            newID,
            createTime,
            editStartDate,
            editEndDate,
            editUser,
            editTitle,
            editStory
        )
        return binding.root
    }
}