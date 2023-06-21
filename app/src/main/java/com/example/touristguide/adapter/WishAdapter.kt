package com.example.touristguide.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.touristguide.R
import com.example.touristguide.databinding.PlaceViewBinding
import com.example.touristguide.model.Result
import com.example.touristguide.model.WishListPlace
import com.google.firebase.firestore.local.LruGarbageCollector

class WishAdapter(private val context:Context,
                  private val WishList:ArrayList<WishListPlace>,
                  private val clickListener: onWishClickListener) : RecyclerView.Adapter<WishAdapter.WishViewHolder>() {

    class WishViewHolder(var binding: PlaceViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(currentPlace:WishListPlace, clickListener: onWishClickListener){
            binding.tvTitle.setText(currentPlace.name)
           if(currentPlace.rating!=null){
               binding.tvRating.setText(currentPlace.rating!!.toString())
           }

            if(currentPlace.icon!=null){
                //var url = "https://maps.googleapis.com/maps/api/place/photo?${currentPlace.photos!![0].photo_reference}&key=AIzaSyBTmys4lYnABAKI4gEbAByuxiL2nCbAm9o"
                Glide.with(binding.tvTitle.context).load(currentPlace.icon).into(binding.ivPic)
            }else{
                binding.ivPic.setImageResource(R.drawable.image)
            }

            //Set a click listener to open a selected news on the browser
            itemView.setOnClickListener(){
                clickListener.onItemClickListener(currentPlace.place_id , currentPlace)
            }

            itemView.setOnLongClickListener(){
                clickListener.onItemLongClickListener((currentPlace))
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishAdapter.WishViewHolder {

        return WishAdapter.WishViewHolder(
            PlaceViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WishAdapter.WishViewHolder, position: Int) {
        holder.bind(WishList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return WishList.size
    }
}

