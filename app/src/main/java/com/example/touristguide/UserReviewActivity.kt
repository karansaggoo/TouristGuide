package com.example.touristguide

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.touristguide.adapter.UserReviewAdapter
import com.example.touristguide.databinding.ActivitySignInBinding
import com.example.touristguide.databinding.ActivityUserReviewBinding
import com.example.touristguide.model.ReviewDB
import com.example.touristguide.model.ReviewRepository

class UserReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserReviewBinding
    private lateinit var ReviewList: ArrayList<ReviewDB>
    lateinit var userReviewAdapter: UserReviewAdapter
    lateinit var reviewRepository: ReviewRepository
    private lateinit var prefs: SharedPreferences
    var email = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityUserReviewBinding.inflate(layoutInflater)
        reviewRepository = ReviewRepository(applicationContext)
        prefs=applicationContext.getSharedPreferences("com.example.touristguide", AppCompatActivity.MODE_PRIVATE)
        email = prefs.getString("USER_EMAIL","").toString()
        setContentView(binding.root)

        ReviewList = ArrayList()
        userReviewAdapter = UserReviewAdapter(applicationContext,ReviewList)
        binding.llRowContainer.adapter = userReviewAdapter
    }
    override fun onStart() {
        super.onStart()

        ReviewList = ArrayList()
        userReviewAdapter = UserReviewAdapter(applicationContext,ReviewList)
        binding.llRowContainer.adapter = userReviewAdapter

        ReviewList.clear()
        reviewRepository.getReviewByEmail(email)
        Log.e("review","${ReviewList}")
        userReviewAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        reviewRepository.getReviewByEmail(email)

        //Get up-to-date favorite list from Firestore
        reviewRepository.userReviewList.observe(this){
                list ->
            ReviewList.clear()
            if(list != null){
                for(review in list){
                    ReviewList.add(review)
                    userReviewAdapter.notifyDataSetChanged()
                }
            }
        }
        Log.e("ReviewList","${ReviewList}")
    }
}