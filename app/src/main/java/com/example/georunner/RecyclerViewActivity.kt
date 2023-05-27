package com.example.georunner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.georunner.databinding.ActivityHomeBinding
import com.example.georunner.databinding.ActivityRecyclerViewBinding
import com.example.georunner.room.User
import com.example.georunner.room.UserRoomRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var activityDataAdapter: ActivityDataAdapter
    private lateinit var activities: List<ActivityData>
    private lateinit var user: User
    private lateinit var userRoomRepository: UserRoomRepository
    private lateinit var menuBarToggle: ActionBarDrawerToggle
    private lateinit var binding : ActivityRecyclerViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate((layoutInflater))
        setContentView(binding.root)

        user = intent.getSerializableExtra("USER_OBJECT") as User
        lifecycleScope.launch(Dispatchers.IO) {
            userRoomRepository = UserRoomRepository(applicationContext)
        }
        Snackbar.make(binding.root, "this password does not match previous password", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        recyclerView = findViewById(R.id.recyclerView)
        activities = user.activities // Get the list of activities from the database or wherever you store it

            activityDataAdapter = ActivityDataAdapter(activities)
        recyclerView.adapter = activityDataAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        setupMenuDrawer(user)


    }

    private fun setupMenuDrawer(user: User) {

        menuBarToggle = ActionBarDrawerToggle(this,binding.drawerLayoutR, R.string.menu_open, R.string.menu_close)
        binding.drawerLayoutR.addDrawerListener(menuBarToggle)
        menuBarToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Log.i("recyc","SETUPMENUDRAWER RACHED")
        binding.navViewR.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.viewMap -> switchToMap(user, "mapActivity")
                R.id.HomeScreen -> switchToMap(user, "home")
            }
            true
        }
    }
    private fun switchToMap(user: User, a : String){
        Log.i("recyc","Smap tried")
        if(a == "mapActivity"){
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("USER_OBJECT", user)
            startActivity(intent)
        }
        else if(a == "home"){
            Log.i("recyc","home tried")
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USER_OBJECT", user)
            startActivity(intent)
        }
    }
}
