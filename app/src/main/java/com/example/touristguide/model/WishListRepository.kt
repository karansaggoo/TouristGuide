package com.example.touristguide.model

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Float

class WishListRepository(private val context: Context) {

    private val db = Firebase.firestore
    private val prefs= context.getSharedPreferences("com.example.touristguide", AppCompatActivity.MODE_PRIVATE)
    private var COLLECTION_USERS = "users"
    private var COLLECTION_ID = "id"

    private var COLLECTION_WISHLIST = "wishlist"
    private var loggedInUserID = ""
    var userWishListLive = MutableLiveData<List<WishListPlace>>()

    init {
            loggedInUserID = prefs.getString("USER_EMAIL","").toString()
    }

    //Add a news into "favorites" sub-collection of the current user document
    fun addFavouriteWish(newWish: WishListPlace) {
        if (loggedInUserID.isNotBlank()) {
            db.collection(COLLECTION_WISHLIST).document(loggedInUserID)
                .collection(COLLECTION_USERS)
                .add(newWish)
            Log.e("harsh","news added")
        } else {
            Log.e("ERROR", "Can't add your story")
        }
    }

    //Get all user's favorite news from the "favorites" sub-collection
    fun getFavouriteWish() {
            db.collection(COLLECTION_WISHLIST)
                .document(loggedInUserID)
                .collection(COLLECTION_USERS)
                .addSnapshotListener(EventListener{snapshot,error ->
                    if (error != null) {
                        return@EventListener
                    }else{
                        Log.d("harsh","error ${error}")
                    }

                    if (snapshot != null) {
                        Log.e("harsh","hellp")
                        val wishList = ArrayList<WishListPlace>()

                        for (documentChange in snapshot.documentChanges) {
                            val wish = WishListPlace()
                            wish.name = documentChange.document.get("name").toString()
                            wish.icon = documentChange.document.get("icon").toString()
                            wish.id = documentChange.document.id
                            wish.place_id = documentChange.document.get("place_id").toString()
                            wish.rating = documentChange.document.get("rating").toString()

                            when (documentChange.type) {
                                DocumentChange.Type.ADDED -> wishList.add(wish)
                                DocumentChange.Type.MODIFIED -> {}
                                DocumentChange.Type.REMOVED -> wishList.remove(wish)
                            }
                        }

                        userWishListLive.postValue(wishList)
                    }else{
                        Log.d("harsh","null")
                    }
                })

    }

    //Delete a favorite news from Firestore with document id equals to the given docId
    fun deleteFromWishList(docId: String) {
        try {
            db.collection(COLLECTION_WISHLIST)
                .document(loggedInUserID)
                .collection(COLLECTION_USERS)
                .document(docId)
                .delete()

        }
        catch (ex: Exception) {
            Log.e("ERROR", "deleteFromFavorite(): Couldn't delete a given place from wishlist")
        }
    }
}