package com.example.touristguide

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
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
            val category = "restaurant"
            val action = HomeScreenDirections.actionHomeScreenToList2(category)
            findNavController().navigate(action)
        }

        binding.searchBtn.setOnClickListener {
            var category = binding.querySearch.text.toString()
            if (category != "accounting" && category != "aquarium" && category != "bar" &&
                category != "bank" && category != "museum" && category != "parking" &&
                category != "bus station"
            ) {

                AlertDialog.Builder(requireContext())
                    .setTitle("Invalid Category")
                    .setMessage(
                        Html.fromHtml(
                        "<b>"+ "Category should be either of the following:<br>"+"</b>"+"accounting<br>aquarium<br>bar<br>bank<br>museum<br>parking<br>bus station",
                            Html.FROM_HTML_MODE_LEGACY
                    ))
                    .setNegativeButton("Ok", null).show()
            }
            else{
                if(category=="bus station")
                    category="bus_station"
                val action = HomeScreenDirections.actionHomeScreenToList2(category)
                findNavController().navigate(action)
            }
        }
        binding.btn2.setOnClickListener {
            val category = "cafe"
            val action = HomeScreenDirections.actionHomeScreenToList2(category)
            findNavController().navigate(action)
        }
        binding.btn3.setOnClickListener {
            val category = "shop"
            val action = HomeScreenDirections.actionHomeScreenToList2(category)
            findNavController().navigate(action)
        }
        binding.btn4.setOnClickListener {
            val category = "library"
            val action = HomeScreenDirections.actionHomeScreenToList2(category)
            findNavController().navigate(action)
        }
        binding.btn5.setOnClickListener {
            val category = "hospital"
            val action = HomeScreenDirections.actionHomeScreenToList2(category)
            findNavController().navigate(action)
        }
        binding.btn6.setOnClickListener {
            val category = "theater"
            val action = HomeScreenDirections.actionHomeScreenToList2(category)
            findNavController().navigate(action)
        }

    }


}

