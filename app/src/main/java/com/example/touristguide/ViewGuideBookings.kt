package com.example.touristguide

import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.touristguide.adapter.TourBookingAdapter
import com.example.touristguide.adapter.WishAdapter
import com.example.touristguide.databinding.FragmentViewGuideBookingsBinding
import com.example.touristguide.databinding.FragmentWishListBinding
import com.example.touristguide.model.GuideRepository
import com.example.touristguide.model.TourBooking
import com.example.touristguide.model.WishListPlace
import com.example.touristguide.model.WishListRepository
import com.google.android.play.integrity.internal.t


class ViewGuideBookings : Fragment() {
    private var _binding: FragmentViewGuideBookingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookingList:ArrayList<TourBooking>
    lateinit var bookingAdapter:TourBookingAdapter
    lateinit var guideRepository: GuideRepository
    private lateinit var prefs: SharedPreferences
    private var email:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        guideRepository = GuideRepository(requireContext())
        prefs=requireContext().getSharedPreferences("com.example.touristguide", AppCompatActivity.MODE_PRIVATE)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewGuideBookingsBinding.inflate(inflater,container,false)
        bookingList = ArrayList()
        email = prefs.getString("USER_EMAIL","").toString()

        bookingAdapter = TourBookingAdapter(requireContext(), bookingList)
        binding.rvViewBooking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvViewBooking.adapter = bookingAdapter
        val view = binding.root

        return view

    }


    override fun onStart() {
        super.onStart()

        bookingList =  ArrayList()
        bookingAdapter = TourBookingAdapter(requireContext(), bookingList)
        binding.rvViewBooking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvViewBooking.adapter = bookingAdapter

        bookingList.clear()
        Log.e("harsh","calling")
        guideRepository.getBookingByEmail(email)
        Log.e("bookinglist","${bookingList}")
        bookingAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        guideRepository.getBookingByEmail(email)
        //Get up-to-date favorite list from Firestore
        guideRepository.bookingList.observe(this){
                list ->
            bookingList.clear()
            if(list != null){
                for(booking in list){
                    bookingList.add(booking)
                    bookingAdapter.notifyDataSetChanged()
                }
            }
        }
        Log.e("wishlist","${bookingList}")
    }



}