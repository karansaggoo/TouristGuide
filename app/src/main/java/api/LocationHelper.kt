package api

import com.google.android.gms.maps.model.LatLng
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationRequest
import android.renderscript.RenderScript
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.lang.Exception
import java.util.*

class LocationHelper private constructor(){
    private val TAG = "LOCATION-HELPER"

    // ----------------
    // singleton
    // ----------------
    companion object {
        val instance = LocationHelper()
    }

    // ----------------
    // permissions properties
    // ----------------
    // NOTE: Because we are abstracting the permissions out into a singelton helper class,
    // we do not have the ability to access the Activity's onPermissionsRequest() function
    // Instead, we use a class property called locationPermissionsGranted to track whether
    // or not the requied permissions were granted or denied.
    var locationPermissionGranted = false

    // this is the permissions requiest code.
    // Recall that it can be any number (in previous examples, we randomly selected the number 1234)
    val REQUEST_CODE_LOCATION = 1234        // set this to any value

    // ----------------
    // locations properties
    // ----------------
    // TODO: Add properties relating to location services

    //contains functions that allows to access device's location
    private var fusedLocationProviderClient :FusedLocationProviderClient?=null

    //used specify configuration information about the location access
    private val locationRequest : com.google.android.gms.location.LocationRequest
    private val UPDATE_LOCATION_INTERVAL = 5000 //5 sec
    var currentLocation = MutableLiveData<Location>()

    init {
        // TODO: Instantiate location related variables here
        locationRequest = com.google.android.gms.location.LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, UPDATE_LOCATION_INTERVAL.toLong()).build()
    }


    /* ----------------------------- START LOCATION SERVICES CODE -------------------------------*/

    // TODO: Functions relating to location services

    fun getFusedLocationProviderClient(context: Context) : FusedLocationProviderClient {
        if (fusedLocationProviderClient == null){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        }

        return fusedLocationProviderClient!!
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(context: Context) : MutableLiveData<Location>?{
        if (locationPermissionGranted){

            this.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener {location ->

                if (location != null){
                    this.currentLocation.value = location
                    Log.e(TAG, "getLastLocation: last location obtained : ${this.currentLocation}", )
                }else{
                    Log.e(TAG, "getLastLocation: Not able to get last known location", )
                }

            }.addOnFailureListener {
                Log.e(TAG, "getLastLocation: Failed to get the last known location ${it}", )
            }

            return this.currentLocation

        }else {
            Log.e(TAG, "getLastLocation: No location permission granted",)
            requestLocationPermission(context)
        }

        return null
    }

    fun performForwardGeocoding(context: Context, location:LatLng) : Address?{

        val geocoder = Geocoder(context, Locale.getDefault())

        try{

            val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)

            if (addressList!!.size > 0) {
                Log.e(TAG, "performForwardGeocoding: address : ${addressList[0]}",)

                val addressObj = addressList[0]

                Log.e(TAG, "performForwardGeocoding: postal code : ${addressObj.postalCode}" +
                        "\nCountry name : ${addressObj.countryName}" +
                        "\nCountry code : ${addressObj.countryCode}" +
                        "\nLocality : ${addressObj.locality}" +
                        "\nthroughfare : ${addressObj.thoroughfare}" +
                        "\nsubthroughfare : ${addressObj.subThoroughfare}", )


                return addressList[0]
            }else{
                Log.e(TAG, "performForwardGeocoding: No address found", )
            }

        }catch (ex: Exception){
            Log.e(TAG, "performForwardGeocoding: Couldn't get the address for the given location ${ex}", )
        }

        return null
    }





    private fun hasFineLocationPermission(context: Context): Boolean {
        // the .checkSelfPermissions() function requires a context variable,
        // so the context is provided via the hasFineLocationPermission's function argument list
        if (ContextCompat.checkSelfPermission(context.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            return false
        }
    }

    // You can simplify the if-else statements into a single line, like this:
    private fun hasCoarseLocationPermission(context: Context): Boolean {
        // the .checkSelfPermissions() function requires a context variable,
        // so the context is provided via th hasCoarseLocationPermission's function argument list
        return ContextCompat.checkSelfPermission(context.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // Utility function to check if the required location permissions were granted
    fun checkPermissions(context: Context) {

        // To get the location, the user should grant both the coarse and fine location permissions
        // Recall that since this code is inside a singleton class, we cannot access the Activity's onPermissionsRequest function
        // Therefore, we are using the locationPermissionsGranted class property to track if user granted permissions or not
        if (hasFineLocationPermission(context) == true && hasCoarseLocationPermission(context) == true) {
            // in this case, both permissions were provided, so
            this.locationPermissionGranted = true
        }
        Log.d(TAG, "checkPermissions: Are location permissions granted? : " + this.locationPermissionGranted)

        // If the user did not grant permissions, or this is the first time we run the app,
        // then ask for permissions
        if (this.locationPermissionGranted == false) {
            Log.d(TAG, "checkPermissions: Permissions not granted, so requesting permission now...")
            requestLocationPermission(context)
        }
    }

    // Function to specify which permissions are needed and potentially show the permissions popup box
    fun requestLocationPermission(context: Context) {
        // create a list of required permissions (android manifest file)
        val listOfRequiredPermission
                = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

        // Show the permissions popup box
        // - request code: it is a number to identify the permissions you are trying to request
        ActivityCompat.requestPermissions((context as Activity), listOfRequiredPermission, REQUEST_CODE_LOCATION)

        // NOTE: In this example, we have purposefully abstracted away the permissions detection into
        // this custom singleton class. Thus, we are not using the activity's onRequestPermissionResult() function
        // to manage the results of when the person presses ALLOW or DENY on the permissions popup.
        // Instead, we track the results by using the this.locationPermissionGranted
        // property. Notice this value gets set in the checkPermissions() function.
    }


}