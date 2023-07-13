package com.example.touristguide

import android.app.AlertDialog
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_g08.api.IAPIResponse
import com.example.project_g08.api.RetrofitInstance
import com.example.touristguide.adapter.PlaceAdapter
import com.example.touristguide.adapter.onPlaceClickListener
import com.example.touristguide.api.IAPIResponse2
import com.example.touristguide.api.LocationHelper
import com.example.touristguide.databinding.FragmentHomeScreenBinding
import com.example.touristguide.databinding.FragmentListBinding
import com.example.touristguide.model.*
import com.google.firebase.firestore.local.LruGarbageCollector
import kotlinx.coroutines.launch

class List : Fragment(),onPlaceClickListener {

    private var _binding : FragmentListBinding? = null
    private lateinit var locationHelper: LocationHelper
    private var result:MutableLiveData<Location>? = null
    private val binding get() = _binding!!
    private  val args:ListArgs by navArgs()
    private lateinit var placeListFromAPI: Place
    private lateinit var placeDetailFromAPI:PlaceDetail
    private lateinit var wishListRepository: WishListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wishListRepository = WishListRepository(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        this.locationHelper = LocationHelper.instance
        result = locationHelper.getLastLocation(requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var PlaceList: ArrayList<com.example.touristguide.model.Result> = ArrayList()

        var adapter: PlaceAdapter = PlaceAdapter(requireContext(), PlaceList, this)
        binding.rvView.layoutManager = LinearLayoutManager(requireContext())
        binding.rvView.adapter = adapter

        var api: IAPIResponse = RetrofitInstance.retrofitService
        var lat = result!!.value!!.latitude
        var log = result!!.value!!.longitude
        var location:String = "${lat},${log}"

        //Retrieve world news from API
        viewLifecycleOwner.lifecycleScope.launch {

            when(args.category){

                "restaurant"-> placeListFromAPI = api.getRestaurant(location)
                "cafe"->placeListFromAPI=api.getCafe(location)
                "hospital"->placeListFromAPI=api.getHospital(location)
                "library"->placeListFromAPI=api.getLibrary(location)
                "shop"->placeListFromAPI=api.getShopping(location)
                "theater"->placeListFromAPI=api.getTheatre(location)
                "bar"->placeListFromAPI=api.getTheatre(location)


            }

            PlaceList.clear()
            PlaceList.addAll(placeListFromAPI.results)
            adapter.notifyDataSetChanged()
            Log.d("world","${PlaceList.size}")
            Log.d("world", "${placeListFromAPI.results}")
        }

        val simpleCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.LEFT) {
                val addDialog = AlertDialog.Builder(requireContext())
                    .setTitle("Do you want to save this ?")
                    .setPositiveButton("Yes") { dialog, which ->
                        var position = viewHolder.adapterPosition
                        var image = ""
                        if(PlaceList[position].icon!=null){
                            image = PlaceList[position].icon.toString()
                        }

                        val wish = WishListPlace(name = PlaceList[position].name, icon =  image, place_id = PlaceList[position].place_id, rating = PlaceList[position].rating.toString())
                        wishListRepository.addFavouriteWish(wish)
                        Toast.makeText(requireContext(), "Place is added", Toast.LENGTH_SHORT).show()
                        adapter.notifyDataSetChanged()

                    }
                    .setNegativeButton("No"){dialog ,which->
                        adapter.notifyDataSetChanged()
                        dialog.dismiss()

                    }
                    .create()
                addDialog.show()
            }
        }
    }
    val helper = ItemTouchHelper(simpleCallback)
    helper.attachToRecyclerView(binding!!.rvView)

}

    override fun onItemClickListener(place_id:String , place:com.example.touristguide.model.Result) {

        val action = ListDirections.actionList2ToDetailFragment( place.place_id,place.icon,place.rating!!,place.name,)
        findNavController().navigate(action)



    }


}