package com.example.touristguide.adapter


import android.app.Activity
import android.content.Context
import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.touristguide.ChattingChannel
import com.example.touristguide.R
import com.example.touristguide.model.Message
import com.example.touristguide.model.UserRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MessageAdapter(context: Context?, resource: Int, objects: List<Message?>?) :
    ArrayAdapter<Message?>(context!!, resource, objects!!) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView =
                (context as Activity).layoutInflater.inflate(R.layout.item_message, parent, false)
        }
        val message = getItem(position)


        if(message!!.sender_id.equals(Firebase.auth.currentUser?.uid)){
            Log.e("left","trfcedtfrcd")
            convertView!!.findViewById<View>(R.id.right_layout).visibility = View.GONE
            val photoImageView = convertView!!.findViewById<View>(R.id.photoImageViewSender) as ImageView
            val messageTextView = convertView.findViewById<View>(R.id.messageTextViewSender) as TextView
            val authorTextView = convertView.findViewById<View>(R.id.nameTextViewSender) as TextView
            val isPhoto = message?.photoUrl!=null
            if (isPhoto) {
                messageTextView.visibility = View.GONE
                photoImageView.visibility = View.VISIBLE
//            Glide.with(photoImageView.context)
//                .load(message.getPhotoUrl())
//                .into(photoImageView)
                Glide.with(photoImageView).load(message?.photoUrl).into(photoImageView)


            } else {
                messageTextView.visibility = View.VISIBLE
                photoImageView.visibility = View.GONE
                messageTextView.setText(message?.Text())
            }
            authorTextView.setText(message?.Name())
        }else{
            convertView!!.findViewById<View>(R.id.left_layout).visibility = View.GONE
            val photoImageView = convertView!!.findViewById<View>(R.id.photoImageView) as ImageView
            val messageTextView = convertView.findViewById<View>(R.id.messageTextView) as TextView
            val authorTextView = convertView.findViewById<View>(R.id.nameTextView) as TextView
            val isPhoto = message?.photoUrl!=null
            if (isPhoto) {
                messageTextView.visibility = View.GONE
                photoImageView.visibility = View.VISIBLE
//            Glide.with(photoImageView.context)
//                .load(message.getPhotoUrl())
//                .into(photoImageView)
                Glide.with(photoImageView).load(message?.photoUrl).into(photoImageView)


            } else {
                messageTextView.visibility = View.VISIBLE
                photoImageView.visibility = View.GONE
                messageTextView.setText(message?.Text())
            }
            authorTextView.setText(message?.Name())

        }



        return convertView
    }
}
