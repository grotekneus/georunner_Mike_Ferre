package com.example.georunner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.georunner.databinding.ActivityHomeBinding
import com.example.georunner.room.User
import com.example.georunner.room.UserRoomRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var menuBarToggle: ActionBarDrawerToggle
    private lateinit var userRoomRepository: UserRoomRepository
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = intent.getSerializableExtra("USER_OBJECT") as User
        lifecycleScope.launch(Dispatchers.IO) {
            userRoomRepository = UserRoomRepository(applicationContext)
        }

        binding = ActivityHomeBinding.inflate((layoutInflater))
        setContentView(binding.root)
        setupMenuDrawer(user)

        binding.totalScoreText.text="total accumulated score: "+ user.score.toString()
        binding.userNameText.text=user.userName
        binding.timeSpentTravelingText.text= "time spent traveling with the app: "+user.timeSpentRunning.toString()
        binding.amountOfGamesPlayedText.text="amount of games played: "+user.gamesPlayed.toString()
        binding.distanceCoverdText.text="distance traveled with the app: "+user.distanceCovered.toString()

    }

    private fun setupMenuDrawer(user: User) {
        menuBarToggle = ActionBarDrawerToggle(this,binding.drawerLayout, R.string.menu_open, R.string.menu_close)
        binding.drawerLayout.addDrawerListener(menuBarToggle)
        menuBarToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.viewMap -> switchToMap(user)
            }
            true
        }
    }
    private fun switchToMap(user: User){
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("USER_OBJECT", user)
        startActivity(intent)

    }
}
