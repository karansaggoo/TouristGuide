package com.example.touristguide

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.touristguide.databinding.FragmentDetailBinding
import com.example.touristguide.databinding.FragmentForgetPasswordBinding
import com.example.touristguide.model.PlaceDetail
import com.google.firebase.auth.FirebaseAuth

class ForgetPassword : Fragment() {
    private var _binding : FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!
    private val mAuth = FirebaseAuth.getInstance()
   var email = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = binding.emailEt.getText().toString()

        binding.button.setOnClickListener{
            resetPassword(email)
            binding.emailEt.setText("")

        }


    }
    fun resetPassword(email:String){
        mAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Reset passwprd email has been sent to register email", Toast.LENGTH_SHORT)
                        .show()
            }
            .addOnFailureListener{

                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT)
                        .show()

            }

    }

}