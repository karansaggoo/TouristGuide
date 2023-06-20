package com.example.touristguide

import android.content.Context
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.touristguide.adapter.PlaceAdapter
import com.example.touristguide.adapter.WishAdapter
import com.example.touristguide.adapter.onWishClickListener
import com.example.touristguide.databinding.FragmentWishListBinding
import com.example.touristguide.model.WishListPlace
import com.example.touristguide.model.WishListRepository
import org.checkerframework.checker.units.qual.s

class WishList : Fragment(),onWishClickListener {
    private var _binding: FragmentWishListBinding? = null
    private val binding get() = _binding!!
    private lateinit var WishList:ArrayList<WishListPlace>
    lateinit var wishAdapter:WishAdapter
     //var WishList = mutableStateListOf<WishListPlace>()
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

         wishAdapter = WishAdapter(requireContext(), WishList, this)
        binding.rvView.layoutManager = LinearLayoutManager(requireContext())
        binding.rvView.adapter = wishAdapter
        val view = binding.root

        return view
    }

    override fun onStart() {
        super.onStart()

        WishList =  ArrayList()
        wishAdapter = WishAdapter(requireContext(), WishList, this)
        binding.rvView.layoutManager = LinearLayoutManager(requireContext())
        binding.rvView.adapter = wishAdapter

        WishList.clear()
        Log.e("harsh","calling")
        wishListRepository.getFavouriteWish()
        Log.e("wishlist","${WishList}")
        wishAdapter.notifyDataSetChanged()
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
                    wishAdapter.notifyDataSetChanged()
                }
            }
        }
        Log.e("wishlist","${WishList}")
    }

    override fun onItemClickListener(place_id: String, place: WishListPlace) {

    }


}
