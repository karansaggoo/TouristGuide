package com.example.touristguide

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.fragment.app.Fragment
import com.example.touristguide.databinding.FragmentGuideProfileBinding
import com.example.touristguide.model.Guide
import com.example.touristguide.model.GuideRepository
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class GuideProfile : Fragment() {
    private var _binding : FragmentGuideProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var guideRepository: GuideRepository
    private final var RC_PHOTO_PICKER =2
    private var selectedImageUri:String?=null
    private lateinit var photoStorageRefeference:StorageReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuideProfileBinding.inflate(inflater, container, false)
        photoStorageRefeference  = FirebaseStorage.getInstance().getReference().child("profile_photos")

        guideRepository = GuideRepository(requireContext())
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        guideRepository.getGuideByEmail("karan")

        guideRepository.guideList.observe(viewLifecycleOwner){
                list ->
            if(list != null){
                for(guide in list){
                    binding.guideName.setText(guide.name)
                    binding.guideTel.setText("tel:${guide.tel}")
                    binding.guideDesc.setText(guide.desc)
                }
           }
            //else{
//                Log.e("list","empty")
//                binding.guideName.setText("You need to create profile first")
//                binding.btn.setText("Create profile")
//            }
        }

        guideRepository.firstTime.observe(viewLifecycleOwner){
            if(it){
                binding.guideName.setText("You need to create profile first")
                binding.btn.setText("Create profile")
            }
        }


    }




}


