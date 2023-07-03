package com.example.touristguide

import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.touristguide.adapter.GuideAdapter
import com.example.touristguide.adapter.WishAdapter
import com.example.touristguide.adapter.onGuideClickListener
import com.example.touristguide.databinding.FragmentGuideSearchBinding
import com.example.touristguide.databinding.FragmentWishListBinding
import com.example.touristguide.model.Guide

import com.example.touristguide.model.GuideRepository


class guideSearch : Fragment(),onGuideClickListener {
    private var _binding: FragmentGuideSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var GuideList:ArrayList<Guide>

    lateinit var guideAdapter: GuideAdapter
    lateinit var GuideRepository : GuideRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GuideRepository = GuideRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuideSearchBinding.inflate(inflater,container,false)
        GuideList = ArrayList()

        guideAdapter = GuideAdapter(requireContext(), GuideList, this)
        binding.rvView.layoutManager = LinearLayoutManager(requireContext())
        binding.rvView.adapter = guideAdapter
        val view = binding.root

        return view
    }

    override fun onStart() {
        super.onStart()

        GuideList =  ArrayList()
        guideAdapter = GuideAdapter(requireContext(), GuideList, this)
        binding.rvView.layoutManager = LinearLayoutManager(requireContext())
        binding.rvView.adapter = guideAdapter

        GuideList.clear()
        Log.e("harsh","calling")
        GuideRepository.getGuideByLoc("toronto")
        Log.e("guidelist","${GuideList}")
        guideAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        GuideRepository.getGuideByLoc("toronto")

        //Get up-to-date favorite list from Firestore
        GuideRepository.guideList.observe(this){
                list ->
            GuideList.clear()
            if(list != null){
                for(guide in list){
                    GuideList.add(guide)
                    guideAdapter.notifyDataSetChanged()
                }
            }
        }
        Log.e("guidelist","${GuideList}")
    }


    override fun onItemClickListener(place_id: String, guide: Guide) {
        TODO("Not yet implemented")
    }


}