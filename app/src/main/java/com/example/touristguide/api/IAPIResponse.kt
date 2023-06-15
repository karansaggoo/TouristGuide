package com.example.project_g08.api



import com.example.touristguide.model.Place
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//URL endpoint for retrieve the news for API
interface IAPIResponse {

    @GET("/maps/api/place/nearbysearch/json?radius=1500&type=restaurant&key=AIzaSyBTmys4lYnABAKI4gEbAByuxiL2nCbAm9o")
    suspend fun getRestaurant(@Query("location") location:String): Place

    @GET("/maps/api/place/nearbysearch/json?radius=1500&type=cafe&key=AIzaSyBTmys4lYnABAKI4gEbAByuxiL2nCbAm9o")
    suspend fun getCafe(@Query("location") location:String): Place

    @GET("/maps/api/place/nearbysearch/json?radius=1500&type=hospital&key=AIzaSyBTmys4lYnABAKI4gEbAByuxiL2nCbAm9o")
    suspend fun getHospital(@Query("location") location:String): Place

    @GET("/maps/api/place/nearbysearch/json?radius=1500&type=library&key=AIzaSyBTmys4lYnABAKI4gEbAByuxiL2nCbAm9o")
    suspend fun getLibrary(@Query("location") location:String): Place

    @GET("/maps/api/place/nearbysearch/json?radius=1500&type=movie_theater&key=AIzaSyBTmys4lYnABAKI4gEbAByuxiL2nCbAm9o")
    suspend fun getTheatre(@Query("location") location:String): Place

    @GET("/maps/api/place/nearbysearch/json?radius=1500&type=shopping_mall&key=AIzaSyBTmys4lYnABAKI4gEbAByuxiL2nCbAm9o")
    suspend fun getShopping(@Query("location") location:String): Place
    @GET("/maps/api/place/nearbysearch/json?radius=1500&key=AIzaSyBTmys4lYnABAKI4gEbAByuxiL2nCbAm9o")
    suspend fun getOther(@Query("location") location:String,@Query("type") type:String): Place






//    @GET("/api/1/news?apikey=pub_14679bf2844c8cc343487f9dd61c5b68ab54f&language=en")
//    suspend fun getByCategory(@Query("category") categoryToRetrieve:String):News
}