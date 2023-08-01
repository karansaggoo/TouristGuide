package com.example.touristguide

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.touristguide.databinding.FragmentBookingBinding
import com.example.touristguide.model.Mailer
import com.example.touristguide.model.TourBooking
import com.example.touristguide.model.TourBookingRepostory
import com.example.touristguide.model.UserRepository
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class Booking : Fragment() {
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!
    private val args: BookingArgs by navArgs()
    private lateinit var prefs: SharedPreferences
    lateinit var tourBookingRepository : TourBookingRepostory
    var bookDate=""
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

      binding.tourDate.setOnClickListener {
          binding.tourDate.setOnClickListener {

              // on below line we are getting
              // the instance of our calendar.
              val c = Calendar.getInstance()

              // on below line we are getting
              // our day, month and year.
              val year = c.get(Calendar.YEAR)
              val month = c.get(Calendar.MONTH)
              val day = c.get(Calendar.DAY_OF_MONTH)

              // on below line we are creating a
              // variable for date picker dialog.
              val datePickerDialog = DatePickerDialog(
                  // on below line we are passing context.
                  requireContext(),
                  { view, year, monthOfYear, dayOfMonth ->
                      // on below line we are setting
                      // date to our edit text.
                      val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                      binding.tourDate.setText(dat)
                  },
                  // on below line we are passing year, month
                  // and day for the selected date in our date picker.
                  year,
                  month,
                  day
              )

              datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

              // at last we are calling show
              // to display our date picker dialog.
              datePickerDialog.show()
          }
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

            tourBookingRepository.addTourBooking(TourBooking(ncusName = cusName, cusEmail = cusEmail, guideEmail = guideEmail, guideName = guideName, tel = guideTel, bookingDate = bookingDate , numOfPMember = numOfMember, paymentMode = paymentMode , cardName = cardName, cardNumber = cardNumber, card_cvv = cardCvv, card_date = cardDate ))
//

            var message = "Your booking with ${guideName} has been confirmed for ${bookingDate}./n Thanks"
            var mailer = Mailer(requireContext(),"${cusEmail}","Booking confirmed",message)
            mailer.execute()


                    }
    }

   inner class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener{
        val TAG = "DatePickerFragment"

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return DatePickerDialog(this.requireActivity(), this,
                LocalDate.now().year,
                LocalDate.now().monthValue-1, LocalDate.now().dayOfMonth)
        }
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

            var myDate = LocalDate.of(year, month+1, dayOfMonth)
            bookDate = myDate.toString()
        }

    }

}