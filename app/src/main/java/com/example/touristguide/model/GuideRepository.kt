package com.example.touristguide.model

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


import java.util.HashMap


class GuideRepository(private val context:Context) {
    private val TAG = this.toString()
    private val db = Firebase.firestore
    //    private val FIELD_USER_ID = "id"
    private val COLLECTION_NAME = "guide"
    private val FIELD_USER_EMAIL = "email"
    private val FIELD_USER_NAME = "name"
    private val FIELD_TEL = "tel"
    private val FIELD_DESC = "desc"
    private val FIELD_LOC = "loc"
    private val sharedPreference = context.getSharedPreferences("com.example.touristguide", Context.MODE_PRIVATE)
    private var editor = sharedPreference.edit()

    fun addUserToDB(newUser: Guide){
        try{
            val data: MutableMap<String, Any> = HashMap()

            data[FIELD_USER_EMAIL] = newUser.email;
            data[FIELD_USER_NAME] = newUser.name;
            data[FIELD_LOC] = newUser.loc
            data[FIELD_TEL] = newUser.tel
            data[FIELD_DESC] = newUser.desc


            db.collection(COLLECTION_NAME).add(data).addOnSuccessListener { docRef ->
                Log.d(TAG, "addGuideToDB: Document added with ID ${docRef.id}")
                editor.putString("USER_DOC_ID",docRef.id)
                editor.commit()

            }.addOnFailureListener{
                Log.e(TAG, "addUserToDB: ${it}")
            }

        }catch(ex: Exception){
            Log.e(TAG, "addUserToDB: ${ex.toString()}")
        }
    }


}