package com.example.touristguide.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


import java.util.HashMap




class TourBookingRepostory(private  val context: Context) {

    private val TAG = this.toString()
    private val db = Firebase.firestore
    var bookingList = MutableLiveData<List<TourBooking>>()
    private val COLLECTION_BOOKING_NAME="Bookings"
    private val FIELD_USER_EMAIL = "cusEmail"
    private val FIELD_GUIDE_EMAIL = "guideEmail"
    private val FIELD_USER_NAME = "ncusName"
    private val FIELD_GUIDE_NAME = "guideName"
    private val FIELD_TEL = "tel"
    private val FIELD_NO_MEMBER = "numOfPMember"
    private val FIELD_DATE = "bookingDate"
    private val FIELD_PAYMENT_MODE = "paymentMode"
    private val FIELD_CARD_NAME = "cardName"
    private val FIELD_CARD_NUMBER = "cardNumber"
    private val FIELD_CARD_DATE = "card_date"
    private val FIELD_CARD_CVV = "card_cvv"
//    private val sharedPreference = context.getSharedPreferences("com.example.touristguide", Context.MODE_PRIVATE)
  //  var firstTime = MutableLiveData<Boolean>(false)
    private var loggedInUserID = ""

//    init {
//        loggedInUserID = sharedPreference.getString("USER_EMAIL","").toString()
//    }

    @SuppressLint("SuspiciousIndentation")
    fun addTourBooking(newUser: TourBooking){
        try{
            val data: MutableMap<String, Any> = HashMap()


            data[FIELD_USER_EMAIL] = newUser.cusEmail;
            data[FIELD_USER_NAME] = newUser.cusName;
            data[FIELD_GUIDE_EMAIL] = newUser.guideEmail;
            data[FIELD_GUIDE_NAME] = newUser.guideName;
            data[FIELD_PAYMENT_MODE] = newUser.paymentMode
            data[FIELD_TEL] = newUser.guideTel
            data[FIELD_NO_MEMBER] = newUser.people
            data[FIELD_DATE] = newUser.bookDate

            data[FIELD_CARD_NAME] = newUser.cardName
            data[FIELD_CARD_NUMBER] = newUser.cardNumber
            data[FIELD_CARD_DATE] = newUser.cardExpiry
            data[FIELD_CARD_CVV] = newUser.cardCVV


            db.collection(COLLECTION_BOOKING_NAME).add(data).addOnSuccessListener { docRef ->
                Log.d(TAG, "addGuideToDB: Document added with ID ${docRef.id}")

            }.addOnFailureListener{
                Log.e(TAG, "addUserToDB: ${it}")
            }

        }catch(ex: Exception){
            Log.e(TAG, "addUserToDB: ${ex.toString()}")
        }
    }

}