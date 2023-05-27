package com.example.georunner

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.example.georunner.databinding.ActivityHomeBinding
import com.example.georunner.room.User
import com.example.georunner.room.UserRoomRepository
import kotlinx.coroutines.Dispatchers
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

        binding.recycleTestButton.setOnClickListener(){
            val intent = Intent(this,RecyclerViewActivity::class.java)
            intent.putExtra("USER_OBJECT", user)
            startActivity(intent)
        }

        binding.totalScoreText.text="total accumulated score: "+ user.score.toString()
        binding.userNameText.text=user.userName
        binding.timeSpentTravelingText.text= "time spent traveling with the app: "+user.timeSpentRunningHours.toString()+":"+user.timeSpentRunningMinutes+":"+user.timeSpentRunningSeconds
        binding.amountOfGamesPlayedText.text="amount of games played: "+user.gamesPlayed.toString()
        binding.distanceCoverdText.text= user.activities.getOrNull(2)?.distance.toString()//"distance traveled with the app: "+user.distanceCovered.toString()

    }


    private fun setupMenuDrawer(user: User) {
        menuBarToggle = ActionBarDrawerToggle(this,binding.drawerLayout, R.string.menu_open, R.string.menu_close)
        binding.drawerLayout.addDrawerListener(menuBarToggle)
        menuBarToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.viewMap -> switchToMap(user, "mapActivity")
                R.id.runHistory -> switchToMap(user, "recyclerview")
            }
            true
        }
    }
    private fun switchToMap(user: User, a : String){
        if(a == "mapActivity"){
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("USER_OBJECT", user)
            startActivity(intent)
        }
        else if(a == "recyclerview"){
            val intent = Intent(this, RecyclerViewActivity::class.java)
            intent.putExtra("USER_OBJECT", user)
            startActivity(intent)
        }
    }
}
