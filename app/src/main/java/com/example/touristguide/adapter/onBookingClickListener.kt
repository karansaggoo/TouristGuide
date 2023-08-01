package com.example.touristguide.adapter

import com.example.touristguide.model.TourBooking
import com.example.touristguide.model.WishListPlace

interface onBookingClickListener {
    fun onItemClickListener(booking:TourBooking)
    fun onItemLongClickListener(booking: TourBooking)
}