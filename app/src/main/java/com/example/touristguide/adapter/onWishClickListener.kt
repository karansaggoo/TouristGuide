package com.example.touristguide.adapter

import com.example.touristguide.model.Result
import com.example.touristguide.model.WishListPlace

interface onWishClickListener {
    fun onItemClickListener(place_id:String , place:WishListPlace)

}