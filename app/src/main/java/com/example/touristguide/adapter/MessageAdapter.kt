package com.example.touristguide.adapter


import android.app.Activity
import android.content.Context
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.touristguide.ChattingChannel
import com.example.touristguide.R
import com.example.touristguide.databinding.ItemMessageBinding
import com.example.touristguide.model.Message
import com.example.touristguide.model.UserRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MessageAdapter(context: Context?, resource: Int, objects: List<Message?>?) :
    ArrayAdapter<Message?>(context!!, resource, objects!!) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        lateinit var itemMessageBinding: ItemMessageBinding
        itemMessageBinding =ItemMessageBinding.inflate(LayoutInflater.from(context),parent,false)
        var item = itemMessageBinding.root
//        var convertView = convertView
//        if (convertView == null) {
//            convertView =
//                (context as Activity).layoutInflater.inflate(R.layout.item_message, parent, false)
//        }
        val message = getItem(position)
        val flag = message!!.sender_id.equals(Firebase.auth.currentUser?.uid)

        if(flag){
            Log.e("left","trfcedtfrcd")
            itemMessageBinding.rightLayout.visibility = View.INVISIBLE
            val photoImageView = itemMessageBinding.photoImageViewSender
            val messageTextView = itemMessageBinding.messageTextViewSender
            val authorTextView = itemMessageBinding.nameTextViewSender
            val isPhoto = message?.photoUrl!=null
            if (isPhoto) {
                itemMessageBinding.imgLayoutSender.visibility = View.VISIBLE
                itemMessageBinding.nameImageViewSender.setText(message?.Name())
                itemMessageBinding.timeStampPhotoSender.setText(message?.timeStamp())
                itemMessageBinding.msgLayoutSender.visibility = View.GONE
//            Glide.with(photoImageView.context)
//                .load(message.getPhotoUrl())
//                .into(photoImageView)
                Glide.with(photoImageView).load(message?.photoUrl).into(photoImageView)


            } else {
                messageTextView.visibility = View.VISIBLE
                itemMessageBinding.imgLayoutSender.visibility = View.GONE
                messageTextView.setText(message?.Text())
                itemMessageBinding.timeStampSender.setText(message?.timeStamp())
            }
            authorTextView.setText(message?.Name())
        }else{
            itemMessageBinding.leftLayout.visibility = View.GONE
            val photoImageView = itemMessageBinding.photoImageView
            val messageTextView = itemMessageBinding.messageTextView
            val authorTextView = itemMessageBinding.nameTextView
            val isPhoto = message?.photoUrl!=null
            if (isPhoto) {
                itemMessageBinding.imgLayout.visibility = View.VISIBLE
                itemMessageBinding.nameImageView.setText(message?.Name())
                itemMessageBinding.timeStampPhoto.setText(message?.timeStamp())
                itemMessageBinding.msgLayout.visibility = View.GONE
//            Glide.with(photoImageView.context)
//                .load(message.getPhotoUrl())
//                .into(photoImageView)
                Glide.with(photoImageView).load(message?.photoUrl).into(photoImageView)


            } else {
                messageTextView.visibility = View.VISIBLE
                itemMessageBinding.imgLayout.visibility = View.GONE
                messageTextView.setText(message?.Text())
                itemMessageBinding.timeStamp.setText(message?.timeStamp())
            }
            authorTextView.setText(message?.Name())

        }



        return item

    }
}
