package com.example.touristguide.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.EventListener
import com.google.firebase.ktx.Firebase
import java.util.HashMap

class UserRepository(private val context: Context) {
    private val TAG = this.toString()

    private val db = Firebase.firestore
    //    private val FIELD_USER_ID = "id"
    private val COLLECTION_NAME = "users"
    private val FIELD_USER_EMAIL = "email"
    private val FIELD_USER_NAME = "name"
    private val FIELD_PASSWORD = "password"
    private val FIELD_ACCOUNT_TYPE = "accountType"
    private val sharedPreference = context.getSharedPreferences("com.example.touristguide", Context.MODE_PRIVATE)
    private var editor = sharedPreference.edit()
    var curUserAccType = ""

    fun addUserToDB(newUser: User){
        try{
            val data: MutableMap<String, Any> = HashMap()

            data[FIELD_USER_EMAIL] = newUser.email;
            data[FIELD_USER_NAME] = newUser.name;
            data[FIELD_PASSWORD] = newUser.password;
            data[FIELD_ACCOUNT_TYPE] = newUser.accountType;

            db.collection(COLLECTION_NAME).add(data).addOnSuccessListener { docRef ->
                Log.d(TAG, "addUserToDB: Document added with ID ${docRef.id}")
                editor.putString("USER_DOC_ID",docRef.id)
                editor.commit()

            }.addOnFailureListener{
                Log.e(TAG, "addUserToDB: ${it}")
            }

        }catch(ex: Exception){
            Log.e(TAG, "addUserToDB: ${ex.toString()}")
        }
    }


    fun getDocID(email:String){
        try{
            db.collection(COLLECTION_NAME)
                .whereEqualTo(FIELD_USER_EMAIL, email)
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
                        for(doc in snapshot.documentChanges){
                            editor.putString("USER_ID",doc.document.id)
                            Log.e(TAG, "searchUserWithEmail: user found  : ${doc.document.id}", )
                            editor.commit()
                        }
                    }else{
                        Log.e(TAG, "searchUserWithEmail: No Documents received from collection")
                    }
                })

        }catch(ex: Exception){
            Log.e(TAG, "getAllFruits: ${ex}")
        }
    }
    fun getName(email:String){
        try{
            db.collection(COLLECTION_NAME)
                .whereEqualTo(FIELD_USER_EMAIL, email)
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
                        for(doc in snapshot.documentChanges){
                            val currentUser : User = doc.document.toObject(User::class.java)
                            editor.putString("USER_NAME",currentUser.name)
                           // editor.putString("USER_ACCOUNT_TYPE",currentUser.accountType)
                            curUserAccType = currentUser.accountType
                            Log.d(TAG, "+++++++++++++++userRepos ${curUserAccType}==================")
                            Log.e(TAG, "searchUserWithEmail: user found================================  : ${currentUser.accountType}", )
                            editor.commit()
                        }
                    }else{
                        Log.e(TAG, "searchUserWithEmail: No Documents received from collection")
                    }
                })

        }catch(ex: Exception){
            Log.e(TAG, "docId : ${ex}")
        }
    }


}