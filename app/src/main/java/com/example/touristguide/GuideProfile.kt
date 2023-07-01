package com.example.touristguide

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.touristguide.databinding.FragmentDetailBinding
import com.example.touristguide.databinding.FragmentGuideProfileBinding

class GuideProfile : Fragment() {
    private var _binding : FragmentGuideProfileBinding? = null
    private val binding get() = _binding!!
    private final var RC_PHOTO_PICKER =2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuideProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.photoPicker.setOnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("images/jpeg")
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true)
            startActivityForResult(Intent.createChooser(intent,"complete action using"),RC_PHOTO_PICKER)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==RC_PHOTO_PICKER && resultCode== AppCompatActivity.RESULT_OK){
            var selectedImageUri = data!!.data
            if (selectedImageUri != null) {
//                var photoRef = mChatPhotoStorageRefeference.child(selectedImageUri.lastPathSegment!!)
//                photoRef.putFile(selectedImageUri).addOnSuccessListener { taskSnapshot ->
//                    var downloadUrl = taskSnapshot.uploadSessionUri
//                    var friendlyMessage = FriendlyMessage(null,mUsername,downloadUrl.toString())
//                    mMessageDatabaseReference.push().setValue(friendlyMessage)
//
            }
        }
    }



    }


