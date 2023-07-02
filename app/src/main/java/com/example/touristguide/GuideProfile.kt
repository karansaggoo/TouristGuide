package com.example.touristguide

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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


class GuideProfile : Fragment() {
    private var _binding : FragmentGuideProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var guideRepository: GuideRepository
    private final var RC_PHOTO_PICKER =2
    private var selectedImageUri:Uri? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuideProfileBinding.inflate(inflater, container, false)
        guideRepository = GuideRepository(requireContext())
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.photoPicker.setOnClickListener {

                pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))

//            var intent = Intent(Intent.ACTION_GET_CONTENT)
//            intent.setType("images/*")
//           // intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true)
//            startActivityForResult(Intent.createChooser(intent,"complete action using"),RC_PHOTO_PICKER)
        }

        binding.profileUpdate.setOnClickListener {
            guideRepository.addUserToDB(Guide("harsh","Harsh",123456,"toronto","YYZ",selectedImageUri.toString()))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==RC_PHOTO_PICKER && resultCode== RESULT_OK){
             selectedImageUri = data!!.data
            if (selectedImageUri != null) {
                binding.profilePic.setImageURI(selectedImageUri)
//                var photoRef = mChatPhotoStorageRefeference.child(selectedImageUri.lastPathSegment!!)
//                photoRef.putFile(selectedImageUri).addOnSuccessListener { taskSnapshot ->
//                    var downloadUrl = taskSnapshot.uploadSessionUri
//                    var friendlyMessage = FriendlyMessage(null,mUsername,downloadUrl.toString())
//                    mMessageDatabaseReference.push().setValue(friendlyMessage)
//
            }
        }
    }
    var pickMedia = registerForActivityResult(
        PickVisualMedia()
    ) { uri: Uri? ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            binding.profilePic.setImageURI(uri)
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }





}


