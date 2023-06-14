package com.example.touristguide

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.touristguide.databinding.FragmentHomeScreenBinding


class HomeScreen : Fragment() {

    private var _binding : FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefs: SharedPreferences
    var name = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = com.example.touristguide.databinding.FragmentHomeScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs=requireContext().getSharedPreferences("com.example.touristguide", AppCompatActivity.MODE_PRIVATE)
        name = prefs.getString("USER_NAME","").toString()
        binding.name.setText("${name}")
        binding.btn1.setOnClickListener {
           // val action = HomeScreenDirections.actionHomeScreenToList2 ()
           // findNavController().navigate(action)
        }

    }

}