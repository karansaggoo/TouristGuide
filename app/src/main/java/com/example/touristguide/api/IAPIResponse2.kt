package com.example.touristguide.api


import com.example.touristguide.model.PlaceDetail
import retrofit2.http.GET
import retrofit2.http.Query

interface IAPIResponse2 {

    @GET("/maps/api/place/details/json?key=AIzaSyBTmys4lYnABAKI4gEbAByuxiL2nCbAm9o")
    suspend fun getDetails(@Query("place_id") place_id:String): PlaceDetail
}