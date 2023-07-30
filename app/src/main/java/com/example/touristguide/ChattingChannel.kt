

package com.example.touristguide
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.touristguide.adapter.MessageAdapter
import com.example.touristguide.databinding.FragmentChattingChannelBinding
import com.example.touristguide.model.Message
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List

class ChattingChannel : Fragment() {
    private var _binding: FragmentChattingChannelBinding? = null
    private val binding get() = _binding!!
    private var mMessageListView: ListView? = null
    private var mMessageAdapter: MessageAdapter? = null
    private var mProgressBar: ProgressBar? = null
    private var mPhotoPickerButton: ImageButton? = null
    private var mMessageEditText: EditText? = null
    private var mSendButton: Button? = null
    private var mUsername: String? = null
    private val args:ChattingChannelArgs by navArgs()
    private lateinit var mMessageDatabaseReference: DatabaseReference
    private var mChildEventListener: ChildEventListener? = null
    private final var RC_PHOTO_PICKER =2
    private lateinit var mChatPhotoStorageRefeference: StorageReference
    private lateinit var prefs: SharedPreferences
    var sender_id:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs=requireContext().getSharedPreferences("com.example.touristguide", AppCompatActivity.MODE_PRIVATE)
        mUsername = prefs.getString("USER_NAME","").toString()
        var email = prefs.getString("USER_EMAIL","").toString()
        mMessageDatabaseReference = FirebaseDatabase.getInstance().getReference().child("messages").child(args.booking.guideEmail.dropLast(4)).child(args.booking.cusEmail.dropLast(4))
        mChatPhotoStorageRefeference  = FirebaseStorage.getInstance().getReference().child("chat_photos")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChattingChannelBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProgressBar = binding.progressBar
        mMessageListView = binding.lv
        mPhotoPickerButton = binding.iv
        mMessageEditText = binding.et
        mSendButton = binding.btnSend

        // Initialize message ListView and its adapter
        val friendlyMessages: List<Message> = ArrayList<Message>()
        val user = Firebase.auth.currentUser
        user?.let {
            val uid = it.uid
            sender_id = uid
        }

        mMessageAdapter = MessageAdapter(requireContext(),com.example.touristguide.R.layout.item_message,friendlyMessages)
        mMessageListView!!.adapter = mMessageAdapter

        // Initialize progress bar
        mProgressBar!!.visibility = ProgressBar.INVISIBLE

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton!!.setOnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("images/jpeg")
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true)
            startActivityForResult(Intent.createChooser(intent,"complete action using"),RC_PHOTO_PICKER)
        }

        fun onActivityResult(requestCode:Int,resultCode:Int,data: Intent){
            super.onActivityResult(resultCode,requestCode,data)
            if(requestCode==RC_PHOTO_PICKER && resultCode== AppCompatActivity.RESULT_OK){
                var selectedImageUri = data.data
                if (selectedImageUri != null) {
                    var photoRef = mChatPhotoStorageRefeference.child(selectedImageUri.lastPathSegment!!)
                    photoRef.putFile(selectedImageUri).addOnSuccessListener { taskSnapshot ->
                        var downloadUrl = taskSnapshot.uploadSessionUri
                        var friendlyMessage = com.example.touristguide.model.Message(sender_id,null,mUsername,downloadUrl.toString())
                        mMessageDatabaseReference.push().setValue(friendlyMessage)
                    }
                }

            }

        }

        // Enable Send button when there's text to send
        mMessageEditText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.toString().trim { it <= ' ' }.length > 0) {
                    mSendButton!!.isEnabled = true
                } else {
                    mSendButton!!.isEnabled = false
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        mMessageEditText!!.filters = arrayOf<InputFilter>(
            InputFilter.LengthFilter(
                DEFAULT_MSG_LENGTH_LIMIT
            )
        )

        // Send button sends a message and clears the EditText
        mSendButton!!.setOnClickListener { // TODO: Send messages on click
            val sdfDate = SimpleDateFormat("yyyy-MM-dd HH:mm") //dd/MM/yyyy
            val now = Date()
            val timeStamp: String = sdfDate.format(now)
            var messagae = com.example.touristguide.model.Message(sender_id,mMessageEditText!!.text.toString(),mUsername,timeStamp,null)
            // Clear input box
            mMessageDatabaseReference.push().setValue(messagae)



            mMessageEditText!!.setText("")
        }

    }

    private fun attachDatabaseReadListener(){
        if(mChildEventListener==null) {
            mChildEventListener = object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val msg = snapshot.getValue<Message>()
                    mMessageAdapter!!.add(msg)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }

            mMessageDatabaseReference.addChildEventListener(mChildEventListener!!)
        }

    }

    override fun onStart() {
        super.onStart()
        attachDatabaseReadListener()
    }
    override fun onPause() {
        super.onPause()
        mMessageAdapter!!.clear()
        detachDatabaseReadListener()
    }

    private fun detachDatabaseReadListener(){
        if(mChildEventListener!=null){
            mMessageDatabaseReference.removeEventListener(mChildEventListener!!)
            mChildEventListener = null
        }
    }



    companion object {
        private const val TAG = "ChattingChannel"
        const val ANONYMOUS = "anonymous"
        const val DEFAULT_MSG_LENGTH_LIMIT = 1000
    }


}