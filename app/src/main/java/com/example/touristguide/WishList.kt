package com.example.touristguide

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.touristguide.databinding.FragmentWishListBinding
import com.example.touristguide.model.WishListPlace
import com.example.touristguide.model.WishListRepository

class WishList : Fragment() {
    private var _binding: FragmentWishListBinding? = null
    private val binding get() = _binding!!
    lateinit var WishList: ArrayList<WishListPlace>
    lateinit var wishListRepository : WishListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wishListRepository = WishListRepository(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWishListBinding.inflate(inflater,container,false)
        WishList = ArrayList()
        val view = binding.root
        return view
    }

    override fun onStart() {
        super.onStart()

        WishList =  ArrayList()
        WishList.clear()
        wishListRepository.getFavouriteWish()
        Log.e("wishlist","${WishList}")
    }

    override fun onResume() {
        super.onResume()
        wishListRepository.getFavouriteWish()

        //Get up-to-date favorite list from Firestore
        wishListRepository.userWishListLive.observe(this){
                list ->
            WishList.clear()
            if(list != null){
                for(wish in list){
                    WishList.add(wish)
                }
            }
        }
    }


}