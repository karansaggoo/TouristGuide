package com.example.touristguide

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.touristguide.adapter.TourBookingAdapter
import com.example.touristguide.adapter.onBookingClickListener
import com.example.touristguide.databinding.FragmentViewGuideBookingsBinding
import com.example.touristguide.model.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ViewGuideBookings : Fragment(),onBookingClickListener {
    private var _binding: FragmentViewGuideBookingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookingList:ArrayList<TourBooking>
    lateinit var bookingAdapter:TourBookingAdapter
    lateinit var guideRepository: GuideRepository
    private lateinit var prefs: SharedPreferences
    private lateinit var userRepository : UserRepository
    private var email:String=""
    private var acctype = ""

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(acctype=="customer"){
            Log.e("type",userRepository.curUserAccType)
            guideRepository.getUserBookingByEmail(email)
        }else{
            Log.e("type",userRepository.curUserAccType)
            guideRepository.getBookingByEmail(email)
        }
        guideRepository.bookingList.observe(viewLifecycleOwner){
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

        guideRepository.isEmpty.observe(viewLifecycleOwner){
            if(it){
                binding.header.visibility = View.VISIBLE
                binding.header.setText("No Bookings Yet")

            }
        }




    }


    override fun onStart() {
        super.onStart()

        bookingList =  ArrayList()
        bookingAdapter = TourBookingAdapter(requireContext(), bookingList,this)
        binding.rvViewBooking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvViewBooking.adapter = bookingAdapter
        bookingList.clear()
        Log.e("harsh","calling")
          acctype = prefs.getString("USER_ACCOUNT_TYPE","").toString()
        guideRepository.isEmpty.observe(viewLifecycleOwner){
            if(it){
                binding.header.visibility = View.VISIBLE
                binding.header.setText("No Bookings Yet")

            }
        }

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
       // guideRepository.getBookingByEmail(email)
        //Get up-to-date favorite list from Firestore
        if(acctype=="customer"){
            Log.e("type",userRepository.curUserAccType)
            guideRepository.getUserBookingByEmail(email)
        }else{
            Log.e("type",userRepository.curUserAccType)
            guideRepository.getBookingByEmail(email)
        }



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

        guideRepository.isEmpty.observe(viewLifecycleOwner){
            if(it){
                binding.header.visibility = View.VISIBLE
                binding.header.setText("No Bookings Yet")

            }
        }

        Log.e("booklist","${bookingList}")
    }

    override fun onItemClickListener(booking: TourBooking) {
        val action = ViewGuideBookingsDirections.actionViewGuideBookingsToViewBookingDetail(booking)
        findNavController().navigate(action)
    }

    override fun onItemLongClickListener(id: String,booking: TourBooking) {

        var date1 = booking.bookingDate
        val date: Date = SimpleDateFormat("dd-MM-yyyy").parse(date1)
        var now:Date = Date()

        if(date.compareTo(now)>=0){
            val alert = AlertDialog.Builder(requireContext())
                .setTitle("Confirmation")
                .setMessage("Your booking is due.Do you want to cancel this booking?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm") { which, dialog ->
                    guideRepository.deleteBooking(id)
                    bookingList.clear()
                    if(acctype=="customer"){

                        guideRepository.getUserBookingByEmail(email)
                    }else{

                        guideRepository.getBookingByEmail(email)
                    }
                    bookingAdapter.notifyDataSetChanged()
                }

            alert.show()
        }else{
            val alert = AlertDialog.Builder(requireContext())
                .setTitle("Confirmation")
                .setMessage("Do you want to  delete the record of this booking?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm") { which, dialog ->
                    guideRepository.deleteBooking(id)
                    bookingList.clear()
                    if(acctype=="customer"){

                        guideRepository.getUserBookingByEmail(email)
                    }else{

                        guideRepository.getBookingByEmail(email)
                    }
                    bookingAdapter.notifyDataSetChanged()
                }

            alert.show()
        }




    }


}