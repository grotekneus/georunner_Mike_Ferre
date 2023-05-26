package com.example.georunner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.georunner.room.User
import com.example.georunner.room.UserRoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var activityDataAdapter: ActivityDataAdapter
    private lateinit var activities: List<ActivityData>
    private lateinit var user: User
    private lateinit var userRoomRepository: UserRoomRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        user = intent.getSerializableExtra("USER_OBJECT") as User
        lifecycleScope.launch(Dispatchers.IO) {
            userRoomRepository = UserRoomRepository(applicationContext)
        }

        recyclerView = findViewById(R.id.recyclerView)
        activities = user.activities // Get the list of activities from the database or wherever you store it

            activityDataAdapter = ActivityDataAdapter(activities)
        recyclerView.adapter = activityDataAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
