package com.example.touristguide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.touristguide.databinding.FragmentBookingBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class Booking : Fragment() {
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!
    private val args:BookingArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookingBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.guideName.setText(args.guide.name)
        binding.guideTel.setText(args.guide.tel)
        binding.guideDesc.setText(args.guide.desc)

        return view

           }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select tour date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

//        binding.tourDate.setOnFocusChangeListener { v, hasFocus ->
//            if(hasFocus){
//                if(!datePicker.isAdded){
//                    datePicker.show(childFragmentManager,"tag")
//                }
//
//            }
//        }

        binding.tourDate.setOnClickListener {
            //datePicker.show(childFragmentManager,"tag")
            if(!datePicker.isAdded){
                datePicker.show(childFragmentManager,"tag")
            }
        }
        datePicker.addOnPositiveButtonClickListener {
            //binding.tourDate.setText(datePicker.toString())
            val utc: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            utc.setTimeInMillis(it)
            val format = SimpleDateFormat("yyyy-MM-dd")
            val formatted: String = format.format(utc.getTime())
            binding.tourDate.setText(formatted)
        }



        datePicker.addOnNegativeButtonClickListener {
            datePicker.dismiss()
        }







    }






}