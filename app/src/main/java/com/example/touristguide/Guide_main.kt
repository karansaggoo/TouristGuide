package com.example.touristguide

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.touristguide.databinding.FragmentGuideMainBinding
import com.example.touristguide.databinding.FragmentGuideProfileBinding
import com.example.touristguide.model.GuideRepository
import com.google.firebase.storage.FirebaseStorage


class Guide_main : Fragment() {
    private lateinit var prefs: SharedPreferences
    private var _binding : FragmentGuideMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGuideMainBinding.inflate(inflater, container, false)

        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs=requireContext().getSharedPreferences("com.example.touristguide", AppCompatActivity.MODE_PRIVATE)
        var name = prefs.getString("USER_NAME","").toString()
        binding.cardProfile.setOnClickListener {
            val action = Guide_mainDirections.actionGuideMainToBooking3()
            findNavController().navigate(action)
        }
        binding.name.setText(name)
    }


}