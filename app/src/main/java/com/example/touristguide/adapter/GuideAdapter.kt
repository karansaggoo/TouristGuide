package com.example.touristguide.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.with
import com.example.touristguide.R
import com.example.touristguide.WishList
import com.example.touristguide.databinding.GuideViewBinding
import com.example.touristguide.databinding.PlaceViewBinding

import com.example.touristguide.model.Guide
import com.example.touristguide.model.WishListPlace

class GuideAdapter(private val context: Context,
                   private val GuideList:ArrayList<Guide>,
                   private val clickListener: onGuideClickListener) : RecyclerView.Adapter<GuideAdapter.GuideViewHolder>() {

    class GuideViewHolder(var binding: GuideViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(currentGuide: Guide, clickListener: onGuideClickListener){
            binding.guideName.setText(currentGuide.name)
            binding.guideTel.setText("tel:${currentGuide.tel}")
//            if(currentPlace.rating!=null){
//                binding.tvRating.setText(currentPlace.rating!!.toString())
//            }

            //binding.profilePic.setImageResource(R.drawable.ic_baseline_person_24)

            //  Glide.with(binding.guideName.context).load(currentGuide.imageUri).into(binding.profilePic)
            if(currentGuide.uri !=null){
                //var url = "https://maps.googleapis.com/maps/api/place/photo?${currentPlace.photos!![0].photo_reference}&key=AIzaSyBTmys4lYnABAKI4gEbAByuxiL2nCbAm9o"
                Glide.with(binding.guideName.context).load(currentGuide.uri).into(binding.profilePic)
                //binding.profilePic.setImageResource(R.drawable.ic_baseline_person_24)

            }else{
               binding.profilePic.setImageResource(R.drawable.ic_baseline_person_24)
            }

            //Set a click listener to open a selected news on the browser
            itemView.setOnClickListener(){
                clickListener.onItemClickListener(currentGuide)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideAdapter.GuideViewHolder {

        return GuideAdapter.GuideViewHolder(
            GuideViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GuideAdapter.GuideViewHolder, position: Int) {
        holder.bind(GuideList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return GuideList.size
    }
}

