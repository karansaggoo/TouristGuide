package com.example.touristguide.model

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.HashMap

class ReviewRepository(private val context: Context) {
    private val TAG = this.toString()
    private val db = Firebase.firestore
    private val COLLECTION_NAME = "reviews"
    private val FIELD_EMAIL = "email"
    private val FIELD_NAME = "name"
    private val FIELD_REVIEW = "review"
    private val sharedPreference = context.getSharedPreferences("com.example.touristguide", Context.MODE_PRIVATE)
    private var editor = sharedPreference.edit()

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
}