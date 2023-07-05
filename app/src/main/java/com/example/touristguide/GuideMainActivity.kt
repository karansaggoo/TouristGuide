package com.example.touristguide

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.compose.compiler.plugins.kotlin.EmptyFunctionMetrics.packageName
import androidx.core.content.ContextCompat.startActivity
import com.example.touristguide.databinding.ActivityGuideMainBinding
import com.example.touristguide.databinding.ActivityMainBinding


class GuideMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuideMainBinding
    private lateinit var prefs: SharedPreferences
    var curUserAccType = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = applicationContext.getSharedPreferences(packageName, AppCompatActivity.MODE_PRIVATE)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
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