package com.example.touristguide

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_g08.api.IAPIResponse
import com.example.project_g08.api.RetrofitInstance
import com.example.touristguide.adapter.PlaceAdapter
import com.example.touristguide.adapter.onPlaceClickListener
import com.example.touristguide.api.IAPIResponse2
import com.example.touristguide.api.LocationHelper
import com.example.touristguide.databinding.FragmentHomeScreenBinding
import com.example.touristguide.databinding.FragmentListBinding
import com.example.touristguide.model.Detail
import com.example.touristguide.model.Place
import com.example.touristguide.model.PlaceDetail
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


            }

            PlaceList.clear()
            PlaceList.addAll(placeListFromAPI.results)
            adapter.notifyDataSetChanged()
            Log.d("world","${PlaceList.size}")
            Log.d("world", "${placeListFromAPI.results}")
        }
    }

    override fun onItemClickListener(place_id:String , place:com.example.touristguide.model.Result) {

        val action = ListDirections.actionList2ToDetailFragment( place.name,place.icon, place.rating!!,place_id)
        findNavController().navigate(action)



    }


}