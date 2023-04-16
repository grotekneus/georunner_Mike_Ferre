package com.example.georunner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.georunner.databinding.ActivityMainBinding
import com.example.georunner.databinding.ActivityMikeBinding
import com.example.georunner.room.User
import com.example.georunner.room.UserRoomRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MikeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMikeBinding
    private lateinit var userRoomRepository: UserRoomRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = intent.getSerializableExtra("USER_OBJECT") as User
        binding = ActivityMikeBinding.inflate(layoutInflater)


        setContentView(binding.root)

        GlobalScope.launch(Dispatchers.IO) {
            userRoomRepository = UserRoomRepository(applicationContext)
        }

        binding.getScoreButton.setOnClickListener {
            var score = 0
            GlobalScope.launch(Dispatchers.IO) {
                score=user.score
                Snackbar.make(binding.root, "score is"+score, Snackbar.LENGTH_LONG).setAction("Action", null).show()

            }
            //Snackbar.make(binding.root, "score is"+score, Snackbar.LENGTH_LONG).setAction("Action", null).show()

        }

        binding.addScoreButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                user.score += 1
                userRoomRepository.userDao.updateUser(user)

            }
            Snackbar.make(binding.root, "score is"+user.score, Snackbar.LENGTH_LONG).setAction("Action", null).show()

        }

    }
}