package com.example.touristguide.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.touristguide.R
import com.example.touristguide.databinding.GuideViewBinding
import com.example.touristguide.model.Guide
import com.example.touristguide.model.TourBooking

class TourBookingAdapter(private val context: Context,
                   private val bookingList:ArrayList<TourBooking>
                   ) : RecyclerView.Adapter<TourBookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(var binding: GuideViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(currentBooking: TourBooking){
            binding.guideName.setText(currentBooking.cusName)
            binding.guideTel.setText("Date:${currentBooking.bookDate}")
//            if(currentPlace.rating!=null){
//                binding.tvRating.setText(currentPlace.rating!!.toString())
//            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourBookingAdapter.BookingViewHolder {

        return TourBookingAdapter.BookingViewHolder(
            GuideViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TourBookingAdapter.BookingViewHolder, position: Int) {
        holder.bind(bookingList[position])
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }
}

