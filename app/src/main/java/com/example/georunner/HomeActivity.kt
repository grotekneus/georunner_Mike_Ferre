package com.example.georunner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.georunner.databinding.ActivityHomeBinding
import com.example.georunner.room.User
import com.example.georunner.room.UserRoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var menuBarToggle: ActionBarDrawerToggle
    private lateinit var userRoomRepository: UserRoomRepository
    private lateinit var user:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = intent.getSerializableExtra("USER_OBJECT") as User
        GlobalScope.launch(Dispatchers.IO) {
            userRoomRepository = UserRoomRepository(applicationContext)
        }

        binding = ActivityHomeBinding.inflate((layoutInflater))
        setContentView(binding.root)
        setupMenuDrawer()
        binding.totalScoreText.text= user.score.toString()
    }
    private fun setupMenuDrawer() {
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
        intent.putExtra("USER_OBJECT", user)
        startActivity(intent)

    }
}
