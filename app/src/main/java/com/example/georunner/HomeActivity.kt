package com.example.georunner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.georunner.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var menuBarToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate((layoutInflater))
        setContentView(binding.root)
<<<<<<< HEAD
        
        
      fun setupMenuDrawer() {
=======
        setupMenuDrawer()
    }
    private fun setupMenuDrawer() {
>>>>>>> 5ad0be549439a389e9ceca6b3658c96ca1ca6b24
        menuBarToggle = ActionBarDrawerToggle(this,binding.drawerLayout, R.string.menu_open, R.string.menu_close)
        binding.drawerLayout.addDrawerListener(menuBarToggle)
        menuBarToggle.syncState()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.viewMap -> switchToMap()
            }
            true
        }
    }
    private fun switchToMap(){
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)

    }
}