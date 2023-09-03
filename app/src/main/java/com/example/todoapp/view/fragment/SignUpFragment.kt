package com.example.todoapp.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerEvents(view)
    }

    private fun registerEvents(view: View) {
        init(view)
        onButtonOkClick()
        onSignInTextClick()
    }

    private fun onSignInTextClick() {
        binding.signuinText.setOnClickListener {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun onButtonOkClick() {
        binding.btnOk.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim()
            val pass = binding.inputPassword.text.toString().trim()
            val confirmpass = binding.inputEmail.text.toString().trim()

            if (
                email.isNotBlank() && email.isNotEmpty() &&
                pass.isNotBlank() && pass.isNotEmpty() &&
                confirmpass.isNotBlank() && confirmpass.isNotEmpty()
               ){
                    if (pass == confirmpass){
                        registerUser(email, pass)
                    } else {
                        Toast.makeText(activity, "Put the same password!", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(activity, "Fill all the fields!", Toast.LENGTH_SHORT).show()
                binding.inputPassword.setText("")
                binding.inputConfirmPassword.setText("")
            }
        }
    }

    private fun registerUser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful)
                navController.navigate(R.id.action_signUpFragment_to_homeFragment)
            else
                Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT).show()

        }
    }

    private fun init(view: View){
        auth = FirebaseAuth.getInstance()
        navController = Navigation.findNavController(view)
    }

}