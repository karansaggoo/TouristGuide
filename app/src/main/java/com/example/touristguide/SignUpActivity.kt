package com.example.touristguide

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.touristguide.databinding.ActivitySignUpBinding
import com.example.touristguide.model.User
import com.example.touristguide.model.UserRepository
import com.google.firebase.auth.FirebaseAuth



class SignUpActivity : AppCompatActivity() {
    private val TAG = this.javaClass.canonicalName
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var prefs: SharedPreferences
    lateinit var userRepository : UserRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivitySignUpBinding.inflate(layoutInflater)
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }
        setContentView(binding.root)

        userRepository = UserRepository(applicationContext)
        mAuth = FirebaseAuth.getInstance()

        binding.signUpButtonCustomer.setOnClickListener {
            validateData("customer")
            clearField()
        }
        binding.signUpButtonGuide.setOnClickListener {
            validateData("guide")
            clearField()
        }

        binding.signInInSignUp.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateData(accountype:String) {
        var validData = true
        var email = ""
        var name = ""
        var password = ""
        var accType = accountype

        if (binding.emailEt.getText().toString().isEmpty()) {
            binding.emailEt.setError("Email Cannot be Empty")
            validData = false
        } else {
            email = binding.emailEt.getText().toString()
        }
        if (binding.nameEt.getText().toString().isEmpty()) {
            binding.nameEt.setError("Name Cannot be Empty")
            validData = false
        } else {
            name = binding.nameEt.getText().toString()
        }
        if (binding.passET.getText().toString().isEmpty()) {
            binding.passET.setError("Password Cannot be Empty")
            validData = false
        } else {
            if (binding.confirmPassEt.getText().toString().isEmpty()) {
                binding.confirmPassEt.setError("Confirm Password Cannot be Empty")
                validData = false
            } else {
                if (!binding.passET.getText().toString()
                        .equals(binding.confirmPassEt.getText().toString())
                ) {
                    binding.confirmPassEt.setError("Both passwords must be same")
                    validData = false
                } else {
                    password = binding.passET.getText().toString()
                }
            }
        }
        if (validData) {
            createAccount(email, name, password ,accType)
        } else {
            Toast.makeText(this, "Please provide correct inputs", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createAccount(email: String,name:String ,password: String , accType: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful) {
                    userRepository.addUserToDB(User(email = email,name=name,password = password, accountType = accType))
                    goToSignIn()
//                        saveToPrefs(email, password)

                } else {
                    Log.e(TAG, "onComplete: Failed to create user with email and password" + task.exception + task.exception!!.localizedMessage)
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun goToSignIn() {
        val mainIntent = Intent(this, SignInActivity::class.java)
        startActivity(mainIntent)
    }

    private fun clearField(){
        binding.emailEt.setText("")
        binding.nameEt.setText("")
        binding.passET.setText("")
        binding.confirmPassEt.setText("")
    }

}