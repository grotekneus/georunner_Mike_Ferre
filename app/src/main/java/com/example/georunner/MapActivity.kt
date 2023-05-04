package com.example.georunner

import android.os.Bundle
import android.os.CountDownTimer
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.example.georunner.databinding.ActivityMainBinding
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.example.georunner.databinding.ActivityMapBinding
import com.example.georunner.room.User
import com.example.georunner.room.UserRoomRepository
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MapActivity : AppCompatActivity(),OnMapReadyCallback{
    private lateinit var binding: ActivityMapBinding
    private lateinit var userRoomRepository: UserRoomRepository


    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.getSerializableExtra("USER_OBJECT") as User

        GlobalScope.launch(Dispatchers.IO) {
            userRoomRepository = UserRoomRepository(applicationContext)
        }




        var mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(@NonNull googleMap:GoogleMap){}

    fun startTimeCounter(user: User){
        var counter = 0
        object : CountDownTimer(65000,1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerView?.text = counter.toString()
                counter++
                //binding.stopTimerButton.setOnClickListener{
                  //  binding.timerView.text = "timer stopped"
                    //addScore(user, calculateScore(counter))//addScore(user, counter+1)
                    //cancel()
                //}
            }

            override fun onFinish() {
                binding.timerView?.text = "Finished"
                addScore(user,calculateScore(counter))
                Snackbar.make(binding.root, "score is"+user.score, Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }.start()
    }

    fun calculateScore(time:Int): Int {
        var score = 0
        score +=time*2
        return score
        //addScore(user, score)
    }
    fun addScore(user:User,score:Int){
        GlobalScope.launch(Dispatchers.IO) {
            user.score += score
            userRoomRepository.userDao.updateUser(user)

        }
        Snackbar.make(binding.root, "score is"+user.score, Snackbar.LENGTH_LONG).setAction("Action", null).show()

    }

}