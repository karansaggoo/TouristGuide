package com.example.touristguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.touristguide.databinding.ActivityForgotPasswordBinding
import com.example.touristguide.databinding.ActivitySignInBinding
import com.example.touristguide.databinding.FragmentForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var mAuth: FirebaseAuth
    var email = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        mAuth = FirebaseAuth.getInstance()
        setContentView(binding.root)


        binding.button.setOnClickListener{
            Log.e("email=======", "***********${email}")
            if (binding.emailEt.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Email can not be empty", Toast.LENGTH_LONG)
                    .show()

            } else {
                email = binding.emailEt.text.toString()
                resetPassword(email)
                binding.emailEt.setText("")
            }

        }
    }
    fun resetPassword(email:String){
        mAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Reset passwprd email has been sent to register email", Toast.LENGTH_LONG)
                    .show()
            }
            .addOnFailureListener{

                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG)
                    .show()

            }

    }
}