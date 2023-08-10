package com.example.touristguide

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.touristguide.databinding.FragmentGuideProfileBinding
import com.example.touristguide.model.Guide
import com.example.touristguide.model.GuideRepository
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class GuideProfile : Fragment() {
    private var _binding : FragmentGuideProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var guideRepository: GuideRepository
    private lateinit var prefs: SharedPreferences
    private final var RC_PHOTO_PICKER =2
    private var selectedImageUri:String?=null
    private lateinit var photoStorageRefeference:StorageReference
    var flag = false
    var id = ""
    var guide = Guide()


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
        prefs=requireContext().getSharedPreferences("com.example.touristguide", AppCompatActivity.MODE_PRIVATE)
        var email = prefs.getString("USER_EMAIL","").toString()
        guideRepository.getGuideByEmail(email)
        guideRepository.guideList.observe(viewLifecycleOwner){
                list ->
            if(list != null){
                for(guide in list){
                    id  =guide.id
                    this.guide = guide
                    binding.profilePic.visibility=View.VISIBLE
                    binding.guideName.visibility=View.VISIBLE
                    binding.guideTel.visibility = View.VISIBLE
                    binding.telIcon.visibility = View.VISIBLE
                    binding.guideDesc.visibility=View.VISIBLE

                    binding.guideName.setText(guide.name)
                    binding.guideTel.setText("tel:${guide.tel}")
                    binding.guideDesc.setText(guide.desc)
                    if(guide.uri!=null){
                        Glide.with(binding.guideName.context).load(guide.uri).into(binding.profilePic)

                    }
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
                flag = it
                binding.guideName.setText("You need to create profile first")
                binding.btn.setText("Create profile")
            }
        }

        binding.btn.setOnClickListener {
            val action = GuideProfileDirections.actionBooking3ToGuideProfile2(flag,guide)
            findNavController().navigate(action)
        }


    }


    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }




}


