package com.example.touristguide

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.input.key.Key.Companion.G
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.touristguide.databinding.FragmentGuideProfileBinding
import com.example.touristguide.databinding.FragmentGuideProfileUpdateBinding
import com.example.touristguide.model.Guide
import com.example.touristguide.model.GuideRepository
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class guideProfileUpdate : Fragment() {
    private var _binding : FragmentGuideProfileUpdateBinding? = null
    private val binding get() = _binding!!
    lateinit var guideRepository: GuideRepository
    private val args:guideProfileUpdateArgs by navArgs()
    private final var RC_PHOTO_PICKER =2
    private var selectedImageUri:String?=null
    private lateinit var prefs: SharedPreferences
    private lateinit var photoStorageRefeference: StorageReference
    var email = ""
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuideProfileUpdateBinding.inflate(inflater, container, false)
        photoStorageRefeference  = FirebaseStorage.getInstance().getReference().child("profile_photos")
        guideRepository = GuideRepository(requireContext())
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs=requireContext().getSharedPreferences("com.example.touristguide", AppCompatActivity.MODE_PRIVATE)
         email = prefs.getString("USER_EMAIL","").toString()
        name = prefs.getString("USER_NAME","").toString()
        binding.guideName.setText(name)
        binding.guideEmail.setText(email)
        binding.guideTel.setText(args.guide.tel)
        binding.guideDesc.setText(args.guide.desc)
        binding.guideLoc.setText(args.guide.loc.lowercase())
        binding.guidePrice.setText(args.guide.price)
        if(args.guide.uri !=null){
            Glide.with(binding.guideName.context).load(args.guide.uri).into(binding.profilePic)
        }
        binding.photoPicker.setOnClickListener {

            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }
        
        if(args.flag){
            binding.profileUpdate.setText("Create Profile")
            binding.profileUpdate.setOnClickListener {
                var tel = binding.guideTel.text.toString()
                var desc= binding.guideDesc.text.toString()
                var loc = binding.guideLoc.text.toString()
                var price = binding.guidePrice.text.toString()
                if(selectedImageUri==null){
                    Log.e("photo null",loc)
                    Log.e("user",desc)
                    Log.e("user",tel)

                    if(binding.guideLoc.text.toString().isEmpty()&&binding.guideDesc.text.toString().isEmpty()&&binding.guideTel.text.toString().isEmpty()&&
                            binding.guidePrice.text.toString().isEmpty()&&binding.guideTel.text.toString().isEmpty()){
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage("All entries are required")
                            .setPositiveButton("OK",
                                DialogInterface.OnClickListener { dialog, id ->
                                    // START THE GAME!
                                    dialog.cancel()
                                })

                        // Create the AlertDialog object and return it
                        builder.create()
                        builder.show()
                    }else{
                        if(binding.guideTel.text!!.toString().length<10){
                            val builder = AlertDialog.Builder(requireContext())
                            builder.setMessage("Enter valid phone number")
                                .setPositiveButton("OK",
                                    DialogInterface.OnClickListener { dialog, id ->
                                        // START THE GAME!
                                        dialog.cancel()
                                    })

                            // Create the AlertDialog object and return it
                            builder.create()
                            builder.show()

                        }else{
                            guideRepository.addUserToDB(Guide(email = email, name = name, tel = tel , desc = desc, loc = loc, uri = "", price = price))

                        }

                    }

                }
                else{
                    if(binding.guideLoc.text.toString().isEmpty()&&binding.guideDesc.text.toString().isEmpty()&&binding.guideTel.text.toString().isEmpty()&&
                        binding.guidePrice.text.toString().isEmpty()){
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage("All entries are required")
                            .setPositiveButton("OK",
                                DialogInterface.OnClickListener { dialog, id ->
                                    // START THE GAME!
                                    dialog.cancel()
                                })

                        // Create the AlertDialog object and return it
                        builder.create()
                        builder.show()
                    }else{
                        if(binding.guideTel.text!!.toString().length<10){

                            val builder = AlertDialog.Builder(requireContext())
                            builder.setMessage("Enter valid phone number")
                                .setPositiveButton("OK",
                                    DialogInterface.OnClickListener { dialog, id ->
                                        // START THE GAME!
                                        dialog.cancel()
                                    })

                            // Create the AlertDialog object and return it
                            builder.create()
                            builder.show()
                        }else{
                            guideRepository.addUserToDB(Guide(email = email, name = name, tel = tel , desc = desc, loc = loc, uri = selectedImageUri!!))

                        }


                    }


                }
                val action = guideProfileUpdateDirections.actionGuideProfile2ToBooking3()
                findNavController().navigate(action)
            }


        }else{
            binding.profileUpdate.setOnClickListener {
                var tel = binding.guideTel.text.toString()
                var desc= binding.guideDesc.text.toString()
                var loc = binding.guideLoc.text.toString()
                var price = binding.guidePrice.text.toString()
                if(selectedImageUri==null){
                    Log.e("user",loc)
                    Log.e("user",desc)
                    Log.e("user",tel)
                    if(binding.guideTel.text!!.toString().length<10){
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage("Enter valid phone number")
                            .setPositiveButton("OK",
                                DialogInterface.OnClickListener { dialog, id ->
                                    // START THE GAME!
                                    dialog.cancel()
                                })

                        // Create the AlertDialog object and return it
                        builder.create()
                        builder.show()

                    }else{
                        guideRepository.updateUserToDB(Guide(id =args.guide.id ,email = email, name = name, tel = tel , desc = desc, loc = loc, uri = args.guide.uri, price = price))
                    }

                }
                else{
                    Log.e("user",loc)
                    Log.e("user",desc)
                    Log.e("user",tel)
                    if(binding.guideTel.text!!.toString().length<10){
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage("Enter valid phone number")
                            .setPositiveButton("OK",
                                DialogInterface.OnClickListener { dialog, id ->
                                    // START THE GAME!
                                    dialog.cancel()
                                })

                        // Create the AlertDialog object and return it
                        builder.create()
                        builder.show()

                    }else{
                        guideRepository.updateUserToDB(Guide(id =args.guide.id ,email = email, name = name, tel = tel , desc = desc, loc = loc, uri = selectedImageUri!!, price = price))

                    }


                    //guideRepository.addUserToDB(Guide(email = email, name = name, tel = tel , desc = desc, loc = loc, imageUri = selectedImageUri!!))

                }
                val action = guideProfileUpdateDirections.actionGuideProfile2ToBooking3()
                findNavController().navigate(action)
            }

        }

    }

    var pickMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            binding.profilePic.setImageURI(uri)
            var photoRef = photoStorageRefeference.child(uri.lastPathSegment!!)
            photoRef.putFile(uri).addOnSuccessListener { taskSnapshot ->
                photoRef.downloadUrl.addOnSuccessListener{
                    selectedImageUri = it.toString()
                }



                Log.d("PhotoPicker", "Selected URI: $uri")
            }
        }else {
            Log.d("PhotoPicker", "No media selected")
        }
    }


}