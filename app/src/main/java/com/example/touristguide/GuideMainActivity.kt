package com.example.touristguide

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.compose.compiler.plugins.kotlin.EmptyFunctionMetrics.packageName
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.touristguide.databinding.ActivityGuideMainBinding
import com.example.touristguide.databinding.ActivityMainBinding


class GuideMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuideMainBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    var curUserAccType = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = applicationContext.getSharedPreferences(packageName, AppCompatActivity.MODE_PRIVATE)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment2) as NavHostFragment
        this.navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_guide, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.log_out -> {
                deletePreferences()
                var intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun deletePreferences() {
        with(prefs.edit()) {
            clear()
            apply()

        }
    }
}