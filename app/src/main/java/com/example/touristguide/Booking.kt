package com.example.touristguide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.touristguide.adapter.GuideAdapter
import com.example.touristguide.databinding.FragmentBookingBinding
import com.example.touristguide.databinding.FragmentGuideSearchBinding

class Booking : Fragment() {
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!
    private val args:BookingArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookingBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.guideName.setText(args.guide.name)
        binding.guideTel.setText(args.guide.tel)
        binding.guideDesc.setText(args.guide.desc)

        return view

           }


}