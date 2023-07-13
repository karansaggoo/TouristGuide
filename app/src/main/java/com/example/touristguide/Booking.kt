package com.example.touristguide

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.touristguide.databinding.FragmentBookingBinding
import com.example.touristguide.model.TourBooking
import com.example.touristguide.model.TourBookingRepostory
import com.example.touristguide.model.UserRepository
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class Booking : Fragment() {
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!
    private val args: BookingArgs by navArgs()
    private lateinit var prefs: SharedPreferences
    lateinit var tourBookingRepository : TourBookingRepostory
    var numOfMember = ""
    var cusName = ""
    var cusEmail =""
    var guideTel= ""
    var guideEmail = ""
    var guideName = ""
    var bookingDate = ""
    var paymentMode = ""
    var cardName =""
    var cardNumber =""
    var cardCvv = ""
    var cardDate =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        val view = binding.root
        tourBookingRepository = TourBookingRepostory(requireContext())
        binding.guideName.setText(args.guide.name)
        binding.guideTel.setText(args.guide.tel)
        binding.guideDesc.setText(args.guide.desc)
        prefs=requireContext().getSharedPreferences("com.example.touristguide", AppCompatActivity.MODE_PRIVATE)
        cusName = prefs.getString("USER_NAME","").toString()
        cusEmail= prefs.getString("USER_EMAIL","").toString()

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

        binding.tourDate.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                if(!datePicker.isAdded){
                    datePicker.show(childFragmentManager,"tag")
                }

            }
        }




        binding.tourDate.setOnClickListener {
            //datePicker.show(childFragmentManager,"tag")
            if (!datePicker.isAdded) {
                datePicker.show(childFragmentManager, "tag")
            }
        }
        datePicker.addOnPositiveButtonClickListener {
            //binding.tourDate.setText(datePicker.toString())
            val utc: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            utc.setTimeInMillis(it)
            val format = SimpleDateFormat("yyyy-MM-dd")
            val formatted: String = format.format(utc.getTime())
            binding.tourDate.setText(formatted).toString()
        }



        datePicker.addOnNegativeButtonClickListener {
            datePicker.dismiss()
        }
        binding.rbCard.setOnClickListener {
            binding.cardLinear.visibility = View.VISIBLE
        }
        binding.rbCash.setOnClickListener {
            binding.cardLinear.visibility = View.GONE
        }





        binding.btn.setOnClickListener{
            numOfMember = binding.numMember.getText().toString()
            guideTel = args.guide.tel
            guideName = args.guide.name
            guideEmail =args.guide.email
            cardName = binding.cardName.getText().toString()
            cardNumber = binding.cardNumber.getText().toString()
            cardCvv = binding.cardCvv.getText().toString()
            cardDate = binding.cardDate.getText().toString()
            bookingDate = binding.tourDate.getText().toString()

            when (binding.rbPayment.checkedRadioButtonId) {
                R.id.rb_cash -> {
                    paymentMode = "Cash"
                }
                R.id.rb_card -> {
                    paymentMode = "Card"


                }
            }

            tourBookingRepository.addTourBooking(TourBooking(cusName = cusName, cusEmail = cusEmail, guideEmail = guideEmail, guideName = guideName, guideTel = guideTel, bookDate = bookingDate , people = numOfMember, paymentMode = paymentMode , cardName = cardName, cardNumber = cardNumber, cardCVV = cardCvv, cardExpiry = cardDate ))


        }


    }

}