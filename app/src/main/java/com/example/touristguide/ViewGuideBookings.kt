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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.touristguide.adapter.TourBookingAdapter
import com.example.touristguide.adapter.WishAdapter
import com.example.touristguide.adapter.onBookingClickListener
import com.example.touristguide.databinding.FragmentViewGuideBookingsBinding
import com.example.touristguide.databinding.FragmentWishListBinding
import com.example.touristguide.model.*
import com.google.android.play.integrity.internal.t


class ViewGuideBookings : Fragment(),onBookingClickListener {
    private var _binding: FragmentViewGuideBookingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookingList:ArrayList<TourBooking>
    lateinit var bookingAdapter:TourBookingAdapter
    lateinit var guideRepository: GuideRepository
    private lateinit var prefs: SharedPreferences
    private lateinit var userRepository : UserRepository
    private var email:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        guideRepository = GuideRepository(requireContext())
        userRepository = UserRepository(requireContext())
        prefs=requireContext().getSharedPreferences("com.example.touristguide", AppCompatActivity.MODE_PRIVATE)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewGuideBookingsBinding.inflate(inflater,container,false)
        bookingList = ArrayList()
        email = prefs.getString("USER_EMAIL","").toString()

        bookingAdapter = TourBookingAdapter(requireContext(), bookingList,this)
        binding.rvViewBooking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvViewBooking.adapter = bookingAdapter
        val view = binding.root

        return view

    }


    override fun onStart() {
        super.onStart()

        bookingList =  ArrayList()
        bookingAdapter = TourBookingAdapter(requireContext(), bookingList,this)
        binding.rvViewBooking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvViewBooking.adapter = bookingAdapter

        bookingList.clear()
        Log.e("harsh","calling")
        var  acctype = prefs.getString("USER_ACCOUNT_TYPE","").toString()
        if(acctype=="customer"){
            Log.e("type",userRepository.curUserAccType)
            guideRepository.getUserBookingByEmail(email)
        }else{
            Log.e("type",userRepository.curUserAccType)
            guideRepository.getBookingByEmail(email)
        }

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
                binding.header.visibility = View.INVISIBLE
                for(booking in list){
                    bookingList.add(booking)
                    bookingAdapter.notifyDataSetChanged()
                }
            }
        }

        if(guideRepository.isEmpty){
            binding.header.visibility = View.VISIBLE
            binding.header.setText("No Bookings Yet")
        }
        Log.e("booklist","${bookingList}")
    }

    override fun onItemClickListener(booking: TourBooking) {
        val action = ViewGuideBookingsDirections.actionViewGuideBookingsToViewBookingDetail(booking)
        findNavController().navigate(action)
    }


}