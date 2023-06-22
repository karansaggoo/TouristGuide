package com.example.touristguide.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.HashMap
import com.google.firebase.firestore.EventListener

class ReviewRepository(private val context: Context) {
    private val TAG = this.toString()
    private val db = Firebase.firestore
    private val COLLECTION_NAME = "reviews"
    private val FIELD_EMAIL = "email"
    private val FIELD_NAME = "name"
    private val FIELD_REVIEW = "review"
    private val sharedPreference = context.getSharedPreferences("com.example.touristguide", Context.MODE_PRIVATE)
    private var editor = sharedPreference.edit()
    var userReviewList = MutableLiveData<List<ReviewDB>>()

    fun addReviewToDB(newReview: ReviewDB){
        try{
            val data: MutableMap<String, Any> = HashMap()

            data[FIELD_EMAIL] = newReview.email;
            data[FIELD_NAME] = newReview.name;
            data[FIELD_REVIEW] = newReview.review;

            db.collection(COLLECTION_NAME).add(data).addOnSuccessListener { docRef ->
                Log.d(TAG, "addReviewToDB: Document added with ID ${docRef.id}")

            }.addOnFailureListener{
                Log.e(TAG, "addReviewToDB: ${it}")
            }

        }catch(ex: Exception){
            Log.e(TAG, "addReviewToDB: ${ex.toString()}")
        }
    }
    fun getReviewByHotelName(hname:String){
        try{
            db.collection(COLLECTION_NAME)
                .whereEqualTo(FIELD_NAME, hname)
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
    fun getReviewByEmail(email:String){
        try{
            db.collection(COLLECTION_NAME)
                .whereEqualTo(FIELD_EMAIL, email)
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

                        val reviewArrayList:MutableList<ReviewDB> = ArrayList<ReviewDB>()
                        for(documentChange in snapshot.documentChanges){
                            val currentReview: ReviewDB = documentChange.document.toObject(ReviewDB::class.java)


                            currentReview.id=documentChange.document.id


                            when(documentChange.type){
                                DocumentChange.Type.ADDED->{reviewArrayList.add(currentReview)}
                                DocumentChange.Type.MODIFIED->{}
                                DocumentChange.Type.REMOVED->{}
                            }
                        }

                        userReviewList.postValue(reviewArrayList)


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
}