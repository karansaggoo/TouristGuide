package com.example.touristguide

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.touristguide.api.LocationHelper
import com.example.touristguide.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdRequest
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var locationHelper: LocationHelper
    private lateinit var mAuth: FirebaseAuth
    private lateinit var prefs: SharedPreferences
    lateinit var mAdView : AdView

    var name = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        prefs=applicationContext.getSharedPreferences(packageName, AppCompatActivity.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        this.locationHelper = LocationHelper.instance
        mAuth = FirebaseAuth.getInstance()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this)
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        this.navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        setupWithNavController(binding.bottomNav, navController)

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.log_out->{
                deletePreferences()
                mAuth.signOut()
                Log.d("---","logout=============")
                var intent = Intent(this,SignInActivity::class.java)
                startActivity(intent )
                finish()
                true
            }
            R.id.review->{
                var intent = Intent(this,UserReviewActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        var result = locationHelper.getLastLocation(this)
    }

    fun deletePreferences() {
        with(prefs.edit()) {
            clear()
            apply()

        }

    }
}