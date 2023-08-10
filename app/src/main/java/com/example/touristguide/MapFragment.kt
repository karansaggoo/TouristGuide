package com.example.touristguide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.touristguide.databinding.FragmentHomeScreenBinding
import com.example.touristguide.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private  var _binding: FragmentMapBinding ?=null
    private val args : MapFragmentArgs by navArgs()

    private val binding get() = _binding!!
    private lateinit var locationToShow : LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val view = binding.root
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationToShow = LatLng(args.lat.toDouble(),args.log.toDouble())
        return view
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        // Add a marker in Sydney and move the camera
        val city = locationToShow
        mMap.addMarker(MarkerOptions().position(locationToShow).title("You're here"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationToShow, 5.0f))

        mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        mMap.isTrafficEnabled = false

        val uiSettings = p0.uiSettings
        uiSettings.isZoomControlsEnabled = true
        uiSettings.isCompassEnabled = true


    }

}

