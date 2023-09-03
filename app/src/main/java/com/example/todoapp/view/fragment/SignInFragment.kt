package com.example.todoapp.view.fragment

import android.os.Build.VERSION_CODES.S
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSignInBinding
import com.example.todoapp.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerEvents(view)
    }

    private fun init(view: View) {
        auth = FirebaseAuth.getInstance()
        navController = Navigation.findNavController(view)
    }

    private fun registerEvents(view: View) {
        init(view)
        onButtonOkClick()
        onSignUpTextClick()
    }

    private fun onSignUpTextClick() {
        binding.signUpText.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun onButtonOkClick() {
        binding.btnOk.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim()
            val pass = binding.inputPassword.text.toString().trim()

            if (
                email.isNotBlank() && email.isNotEmpty() &&
                pass.isNotBlank() && pass.isNotEmpty()
            ){
                loginUser(email, pass)
            } else {
                Toast.makeText(activity, "Fill all the fields!", Toast.LENGTH_SHORT).show()
                binding.inputPassword.setText("")
            }
        }
    }

    private fun loginUser(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate(R.id.action_signInFragment_to_homeFragment)
            } else {
                Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}