package com.example.touristguide.adapter

import com.example.touristguide.model.Result

interface onPlaceClickListener {
    fun onItemClickListener(place_id:String , place:Result)

}