package com.weiyung.intotheforest.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.weiyung.intotheforest.NavigationDirections
import com.weiyung.intotheforest.R
import com.weiyung.intotheforest.database.User
import com.weiyung.intotheforest.databinding.FragmentLoginBinding
import com.weiyung.intotheforest.ext.getVmFactory
import com.weiyung.intotheforest.util.UserManager.isLoggedIn


class LoginFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel> { getVmFactory() }
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.signInButton.setOnClickListener {
            signIn()
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            Log.i(TAG,"check enter : viewModel.user.observe")
            Log.i(TAG,"$isLoggedIn")
            it?.let {
                when {
                    isLoggedIn -> {
                        Log.i(TAG,"check enter : isLoggedIn")
                        findNavController().navigate(
                            NavigationDirections.navigateToHomeFragment()
                        )
                    }
                }
                viewModel.navigateComplete()
            }
        })
        binding.lottieBirds.repeatCount = -1
        binding.lottieBirds.playAnimation()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val singInIntent = googleSignInClient.signInIntent
        startActivityForResult(singInIntent, SIGN_IN)
    }

    companion object {
        const val SIGN_IN = 9001
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val email = account.email
                firebaseAuthWithGoogle(account.idToken!!)

                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id + "email:$email")
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.login_success),
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.login_fail),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    when {
                        currentUser != null -> {
                            val user = currentUser.displayName?.let {
                                currentUser.email?.let { it1 ->
                                    User(
                                        id = currentUser.uid,
                                        name = it,
                                        email = it1,
                                        picture = currentUser.photoUrl.toString()
                                    )
                                }
                            }
                            Log.i(TAG,"user: $user , currentUser: $currentUser")
                            viewModel.addUser(user)
                            Log.i(TAG,"loginFragment addUser: ${viewModel.user.value}")
                            viewModel.getUser(user)
                            Log.i(TAG,"loginFragment getUser: ${viewModel.user.value}")
                        }
                    }
                    Log.d(TAG, "signInWithCredential:success")
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)

                }
            }
    }

}