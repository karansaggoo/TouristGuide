package com.example.touristguide.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


import java.util.HashMap


class GuideRepository(private val context:Context) {
    private val TAG = this.toString()

    private val db = Firebase.firestore
    var guideList = MutableLiveData<List<Guide>>()
    var bookingList = MutableLiveData<List<TourBooking>>()
    //    private val FIELD_USER_ID = "id"
    private val COLLECTION_NAME = "guide"
    private val COLLECTION_BOOKING_NAME="Bookings"
    private val FIELD_USER_EMAIL = "email"
    private val FIELD_USER_NAME = "name"
    private val FIELD_TEL = "tel"
    private val FIELD_DESC = "desc"
    private val FIELD_LOC = "loc"
    private val FIELD_URI="uri"
    private val FIELD_ID = "id"
    private val FIELD_PRICE = "price"
    private val sharedPreference = context.getSharedPreferences("com.example.touristguide", Context.MODE_PRIVATE)
    var firstTime = MutableLiveData<Boolean>(false)
    private var loggedInUserID = ""
    var isEmpty = MutableLiveData<Boolean>(false)

    init {
        loggedInUserID = sharedPreference.getString("USER_EMAIL","").toString()
    }

    fun addUserToDB(newUser: Guide){
        try{
            val data: MutableMap<String, Any> = HashMap()

            data[FIELD_ID] = newUser.id
            data[FIELD_USER_EMAIL] = newUser.email;
            data[FIELD_USER_NAME] = newUser.name;
            data[FIELD_LOC] = newUser.loc
            data[FIELD_TEL] = newUser.tel
            data[FIELD_DESC] = newUser.desc
            data[FIELD_URI]=newUser.imageUri
            data[FIELD_PRICE] = newUser.price


            db.collection(COLLECTION_NAME).add(data).addOnSuccessListener { docRef ->
                Log.d(TAG, "addGuideToDB: Document added with ID ${docRef.id}")

            }.addOnFailureListener{
                Log.e(TAG, "addUserToDB: ${it}")
            }

        }catch(ex: Exception){
            Log.e(TAG, "addUserToDB: ${ex.toString()}")
        }
    }
    fun getGuideByLoc(location:String){
        try{
            db.collection(COLLECTION_NAME)
                .whereEqualTo(FIELD_LOC, location)
                .addSnapshotListener(EventListener{ snapshot, error ->
                    if (error != null){
                        Log.e(TAG, "searchGuideWithLocation: Listening to collection documents FAILED ${error}")
                        return@EventListener
                    }

                    if (snapshot != null){
                        Log.e(
                            TAG,
                            "searchGuideWithLocation: ${snapshot.size()} Received the documents from collection ${snapshot}"
                        )

                        if(snapshot.size()==0){
                            firstTime.value=true
                        }


                        val guideArrayList:MutableList<Guide> = ArrayList<Guide>()
                        for(documentChange in snapshot.documentChanges){
                            val currentGuide: Guide = documentChange.document.toObject(Guide::class.java)


                            currentGuide.id=documentChange.document.id

                            when(documentChange.type){
                                DocumentChange.Type.ADDED->{guideArrayList.add(currentGuide)}
                                DocumentChange.Type.MODIFIED->{}
                                DocumentChange.Type.REMOVED->{}
                            }
                        }

                        guideList.postValue(guideArrayList)
                        Log.e("datafromfirebase","${guideArrayList}")


                        //process the received documents
                        //save the doc ID to the SharedPreferences
//                        for(doc in snapshot.documentChanges){
//                            val currentUser : User = doc.document.toObject(User::class.java)
//                            editor.putString("USER_NAME",currentUser.name)
//                            Log.e(TAG, "searchUserWithEmail: user found  : ${currentUser.name}", )
//                            editor.commit()
//                        }
                    }else{
                        Log.e(TAG, "searchUserWithEmail: No Documents received from collection")
                    }
                })

        }catch(ex: Exception){
            Log.e(TAG, "docId : ${ex}")
        }
    }

    fun getGuideByEmail(email:String){
        try{
            db.collection(COLLECTION_NAME)
                .whereEqualTo(FIELD_USER_EMAIL, email)
                .addSnapshotListener(EventListener{ snapshot, error ->
                    if (error != null){
                        Log.e(TAG, "searchGuideWithLocation: Listening to collection documents FAILED ${error}")
                        return@EventListener
                    }

                    if (snapshot != null){
                        Log.e(
                            TAG,
                            "searchGuideWithEmail: ${snapshot.size()} Received the documents from collection ${snapshot}"
                        )

                        if(snapshot.size()==0){
                            firstTime.value=true
                            Log.e("tvFd","ytgfdx")
                        }

                        val guideArrayList:MutableList<Guide> = ArrayList<Guide>()
                        for(documentChange in snapshot.documentChanges){
                            val currentGuide: Guide = documentChange.document.toObject(Guide::class.java)


                            currentGuide.id=documentChange.document.get("id").toString()

                            when(documentChange.type){
                                DocumentChange.Type.ADDED->{guideArrayList.add(currentGuide)}
                                DocumentChange.Type.MODIFIED->{}
                                DocumentChange.Type.REMOVED->{}
                            }
                        }

                        guideList.postValue(guideArrayList)
                        Log.e("datafromfirebase","${guideArrayList}")


                        //process the received documents
                        //save the doc ID to the SharedPreferences
//                        for(doc in snapshot.documentChanges){
//                            val currentUser : User = doc.document.toObject(User::class.java)
//                            editor.putString("USER_NAME",currentUser.name)
//                            Log.e(TAG, "searchUserWithEmail: user found  : ${currentUser.name}", )
//                            editor.commit()
//                        }
                    }else{
                        Log.e(TAG, "searchUserWithEmail: No Documents received from collection")
                    }
                })

        }catch(ex: Exception){
            Log.e(TAG, "docId : ${ex}")
        }
    }


    fun updateUserToDB(newUser: Guide){
        try{
            val data: MutableMap<String, Any> = HashMap()

            data[FIELD_USER_EMAIL] = newUser.email;
            data[FIELD_USER_NAME] = newUser.name;
            data[FIELD_LOC] = newUser.loc
            data[FIELD_TEL] = newUser.tel
            data[FIELD_DESC] = newUser.desc
            data[FIELD_URI]=newUser.imageUri


            db.collection(COLLECTION_NAME).document(newUser.id).update(data).addOnSuccessListener {
                Log.e(TAG,"update successfull")
            }
            .addOnFailureListener{
                Log.e(TAG, "update unsuccessful")
            }

        }catch(ex: Exception){
            Log.e(TAG, "updateUserToDB: ${ex.toString()}")
        }
    }



    fun addTourBooking(newBooking:TourBooking) {
        try{
            db.collection(COLLECTION_BOOKING_NAME).add(newBooking).addOnSuccessListener { docRef ->
                Log.d(TAG, "addBookingToDB: Document added with ID ${docRef.id}")

            }.addOnFailureListener{
                Log.e(TAG, "addBookingToDB: ${it}")
            }
        }catch(ex: Exception){
            Log.e(TAG, "addBookingToDB: ${ex.toString()}")
        }
    }


    fun getBookingByEmail(email:String){
        try{
            db.collection(COLLECTION_BOOKING_NAME)
                .whereEqualTo("guideEmail", email)
                .addSnapshotListener(EventListener{ snapshot, error ->
                    if (error != null){
                        Log.e(TAG, "searchGuideBookingsWithEmail: Listening to collection documents FAILED ${error}")
                        return@EventListener
                    }

                    if (snapshot != null){
                        Log.e(
                            TAG,
                            "searchGuideBookingsWithEmail: ${snapshot.size()} Received the documents from collection ${snapshot}"
                        )


                        val guideBookingArrayList:MutableList<TourBooking> = ArrayList<TourBooking>()
                        for(documentChange in snapshot.documentChanges){
                            val currentBooking: TourBooking = documentChange.document.toObject(TourBooking::class.java)
                            currentBooking.id=documentChange.document.get("id").toString()

                            when(documentChange.type){
                                DocumentChange.Type.ADDED->{guideBookingArrayList.add(currentBooking)}
                                DocumentChange.Type.MODIFIED->{}
                                DocumentChange.Type.REMOVED->{guideBookingArrayList.remove(currentBooking)}
                            }
                        }

                        bookingList.postValue(guideBookingArrayList)
                        Log.e("datafromfirebase","${guideBookingArrayList}")


                        //process the received documents
                        //save the doc ID to the SharedPreferences
//                        for(doc in snapshot.documentChanges){
//                            val currentUser : User = doc.document.toObject(User::class.java)
//                            editor.putString("USER_NAME",currentUser.name)
//                            Log.e(TAG, "searchUserWithEmail: user found  : ${currentUser.name}", )
//                            editor.commit()
//                        }
                    }else{
                        Log.e(TAG, "searchGuideBookingsWithEmail: No Documents received from collection")
                    }
                })

        }catch(ex: Exception){
            Log.e(TAG, "docId : ${ex}")
        }

    }

    fun getUserBookingByEmail(email:String){
        try{
            db.collection(COLLECTION_BOOKING_NAME)
                .whereEqualTo("cusEmail", email)
                .addSnapshotListener(EventListener{ snapshot, error ->
                    if (error != null){
                        Log.e(TAG, "searchUserBookingsWithEmail: Listening to collection documents FAILED ${error}")
                        return@EventListener
                    }

                    if (snapshot != null){
                        Log.e(
                            TAG,
                            "searchUserBookingsWithEmail: ${snapshot.size()} Received the documents from collection ${snapshot}"
                        )

                        if(snapshot.size()==0){
                            isEmpty.value = true
                        }


                        val guideBookingArrayList:MutableList<TourBooking> = ArrayList<TourBooking>()
                        for(documentChange in snapshot.documentChanges){
                            val currentBooking: TourBooking = documentChange.document.toObject(TourBooking::class.java)
                            currentBooking.guideName = documentChange.document.get("guideName").toString()
                            currentBooking.bookingDate = documentChange.document.get("bookingDate").toString()
                            currentBooking.id = documentChange.document.id
                            currentBooking.cusEmail  =documentChange.document.get("cusEmail").toString()
                            currentBooking.cardName=documentChange.document.get("cardName").toString()
                            currentBooking.cardNumber=documentChange.document.get("cardNumber").toString()
                            currentBooking.card_cvv=documentChange.document.get("card_cvv").toString()
                            currentBooking.card_date=documentChange.document.get("card_date").toString()
                            currentBooking.ncusName=documentChange.document.get("ncusName").toString()
                            currentBooking.numOfPMember=documentChange.document.get("numOfMember").toString()
                            currentBooking.paymentMode=documentChange.document.get("paymentMode").toString()
                            currentBooking.guideEmail=documentChange.document.get("guideEmail").toString()
                            currentBooking.tel=documentChange.document.get("tel").toString()

                            when(documentChange.type){
                                DocumentChange.Type.ADDED->{guideBookingArrayList.add(currentBooking)}
                                DocumentChange.Type.MODIFIED->{}
                                DocumentChange.Type.REMOVED->{guideBookingArrayList.remove(currentBooking)}
                            }
                        }

                        bookingList.postValue(guideBookingArrayList)
                        Log.e("datafromfirebase","${guideBookingArrayList}")


                        //process the received documents
                        //save the doc ID to the SharedPreferences
//                        for(doc in snapshot.documentChanges){
//                            val currentUser : User = doc.document.toObject(User::class.java)
//                            editor.putString("USER_NAME",currentUser.name)
//                            Log.e(TAG, "searchUserWithEmail: user found  : ${currentUser.name}", )
//                            editor.commit()
//                        }
                    }else{
                        Log.e(TAG, "searchGuideBookingsWithEmail: No Documents received from collection")
                    }
                })

        }catch(ex: Exception){
            Log.e(TAG, "docId : ${ex}")
        }

    }


    fun deleteBooking(docId: String) {
        try {
            db.collection(COLLECTION_BOOKING_NAME)
                .document(docId)
                .delete()
            Log.e("delete","successful")

        }
        catch (ex: Exception) {
            Log.e("ERROR", "delete: Couldn't delete")
        }
    }





}




