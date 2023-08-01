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
    var bookingDate = ""

//    init {
//        loggedInUserID = sharedPreference.getString("USER_EMAIL","").toString()
//    }

    @SuppressLint("SuspiciousIndentation")
    fun addTourBooking(newUser: TourBooking){
        try{
            val data: MutableMap<String, Any> = HashMap()


            data[FIELD_USER_EMAIL] = newUser.cusEmail;
            data[FIELD_USER_NAME] = newUser.ncusName;
            data[FIELD_GUIDE_EMAIL] = newUser.guideEmail;
            data[FIELD_GUIDE_NAME] = newUser.guideName;
            data[FIELD_PAYMENT_MODE] = newUser.paymentMode
            data[FIELD_TEL] = newUser.tel
            data[FIELD_NO_MEMBER] = newUser.numOfPMember
            data[FIELD_DATE] = newUser.bookingDate

            data[FIELD_CARD_NAME] = newUser.cardName
            data[FIELD_CARD_NUMBER] = newUser.cardNumber
            data[FIELD_CARD_DATE] = newUser.card_date
            data[FIELD_CARD_CVV] = newUser.card_cvv


            db.collection(COLLECTION_BOOKING_NAME).add(data).addOnSuccessListener { docRef ->
                Log.d(TAG, "addGuideToDB: Document added with ID ${docRef.id}")

            }.addOnFailureListener{
                Log.e(TAG, "addUserToDB: ${it}")
            }

        }catch(ex: Exception){
            Log.e(TAG, "addUserToDB: ${ex.toString()}")
        }
    }

    fun checkBookingDate(date:String){
        try{
            db.collection(COLLECTION_BOOKING_NAME)
                .whereEqualTo(FIELD_DATE, date)
                .addSnapshotListener(EventListener{ snapshot, error ->
                    if (error != null){
                        Log.e(TAG, "searchUserWithEmail: Listening to collection documents FAILED ${error}")
                        return@EventListener
                    }

                    if (snapshot != null){
                        Log.e(
                            TAG,
                            "searchUserWithEmail: ${snapshot.size()} Received the documents from collection ${snapshot}"
                        )

                        //process the received documents
                        //save the doc ID to the SharedPreferences
                            for(documentChange in snapshot.documentChanges){
                                val currentTour: TourBooking = documentChange.document.toObject(TourBooking::class.java)


                                bookingDate = currentTour.bookingDate


                            }
                    }else{
                        Log.e(TAG, "searchUserWithEmail: No Documents received from collection")
                    }
                })

        }catch(ex: Exception){
            Log.e(TAG, "getAllFruits: ${ex}")
        }
    }




}