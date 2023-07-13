package com.example.touristguide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.touristguide.adapter.GuideAdapter
import com.example.touristguide.databinding.FragmentGuideSearchBinding
import com.example.touristguide.databinding.FragmentViewBookingDetailBinding

class ViewBookingDetail : Fragment() {
    private var _binding: FragmentViewBookingDetailBinding? = null
    private val binding get() = _binding!!
    private val args:ViewBookingDetailArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewBookingDetailBinding.inflate(inflater,container,false)

        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chat.setOnClickListener {
            var action = ViewBookingDetailDirections.actionViewBookingDetailToChattingChannel(args.booking)
            findNavController().navigate(action)
        }
    }


}