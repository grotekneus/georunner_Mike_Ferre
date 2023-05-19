package com.example.georunner

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
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

    private var startTime: Int = 0
    private var startDistance: Int = 0

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
            addScore(user, 1)
        }

        binding.startTimerButton.setOnClickListener{
            //var counter = 0
            Snackbar.make(binding.root, "timer gestart", Snackbar.LENGTH_LONG).setAction("Action", null).show()

            startTimeCounter(user)
        }
    }

    fun setStartTime(startTime: Int){
        this.startTime=startTime
    }

    fun setStartDistance(startDistance: Int){
        this.startDistance=startDistance
    }
    fun calculateScoreTime(time:Int): Int {
        var score = 0
        score = time*2

        return score
        //addScore(user, score)
    }

    fun calculateScoreDistance(distance:Int): Int{
        var score=0
        score += distance
        return score
    }
    fun addScore(user:User,score:Int){
        GlobalScope.launch(Dispatchers.IO) {
            user.score += score
            userRoomRepository.userDao.updateUser(user)

        }
        Snackbar.make(binding.root, "score is"+user.score, Snackbar.LENGTH_LONG).setAction("Action", null).show()

    }
    fun startTimeCounter(user: User){
        var counter = 0
        object : CountDownTimer((startTime*1000).toLong(),1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerView.text = counter.toString()
                counter++
                binding.stopTimerButton.setOnClickListener{
                    binding.timerView.text = "timer stopped"
                    addScore(user, calculateScoreTime(counter))//addScore(user, counter+1)
                    cancel()
                }
            }

            override fun onFinish() {
                binding.timerView.text = "Finished"
            }
        }.start()
    }
}

/*
var counter = 0
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
      title = "KotlinApp"
   }
   fun startTimeCounter(view: View) {
      val countTime: TextView = findViewById(R.id.countTime)
      object : CountDownTimer(50000, 1000) {
         override fun onTick(millisUntilFinished: Long) {
            countTime.text = counter.toString()
            counter++
         }
         override fun onFinish() {
            countTime.text = "Finished"
         }
      }.start()
   }
 */