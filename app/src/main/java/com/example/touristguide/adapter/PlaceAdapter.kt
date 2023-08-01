package com.example.touristguide.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.touristguide.R
import com.example.touristguide.databinding.PlaceViewBinding
import com.example.touristguide.model.Result
import com.google.firebase.firestore.local.LruGarbageCollector

class PlaceAdapter(private val context:Context,
private val PlaceList:ArrayList<Result>,
private val clickListener: onPlaceClickListener) : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(var binding: PlaceViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(currentPlace:Result, clickListener: onPlaceClickListener){
            binding.tvTitle.setText(currentPlace.name)
           if(currentPlace.rating!=null){
               binding.tvRating.setText(currentPlace.rating!!.toString())
           }

            if(currentPlace.photos!=null){
                var url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=${currentPlace.photos?.get(0)!!.photo_reference}&key=AIzaSyBTmys4lYnABAKI4gEbAByuxiL2nCbAm9o"
                Glide.with(binding.tvTitle.context).load(url).into(binding.ivPic)
            }else{
                binding.ivPic.setImageResource(R.drawable.image)
            }

            //Set a click listener to open a selected news on the browser
            itemView.setOnClickListener(){
                clickListener.onItemClickListener(currentPlace.place_id , currentPlace)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {

        return PlaceViewHolder(PlaceViewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(PlaceList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return PlaceList.size
    }
}

