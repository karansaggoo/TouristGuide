package com.example.touristguide

import android.app.AlertDialog
import android.content.DialogInterface
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
        setTitle("Forget Password")


        binding.button.setOnClickListener{
            Log.e("email=======", "***********${email}")
            if (binding.emailEt.text.toString().isEmpty()) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Email cannot be emptied")
                    .setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialog, id ->
                            // START THE GAME!
                            dialog.cancel()
                        })

                // Create the AlertDialog object and return it
                builder.create()
                builder.show()

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