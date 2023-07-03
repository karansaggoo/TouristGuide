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
    //    private val FIELD_USER_ID = "id"
    private val COLLECTION_NAME = "guide"
    private val FIELD_USER_EMAIL = "email"
    private val FIELD_USER_NAME = "name"
    private val FIELD_TEL = "tel"
    private val FIELD_DESC = "desc"
    private val FIELD_LOC = "loc"
    private val FIELD_URI="uri"
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
            data[FIELD_URI]=newUser.imageUri


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
    fun getGuideByLoc(location:String){
        try{
            db.collection(COLLECTION_NAME)
                .whereEqualTo(FIELD_LOC, location)
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