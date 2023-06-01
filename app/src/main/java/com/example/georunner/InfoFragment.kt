package com.example.georunner

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.georunner.room.User
import com.google.android.material.snackbar.Snackbar
import java.util.*
private var timerIsRunning: Boolean=false
private lateinit var user:User
private lateinit var handler: Handler
private var timer: Timer? = null
private var distance : Double = 0.0

class InfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        handler = Handler(Looper.getMainLooper())


        user=(activity as MapActivity).getUser()
        val runDistance = view.findViewById<TextView>(R.id.runDistance)
        runDistance?.text = "Distance"
        val button = view.findViewById<Button>(R.id.startTimerButton)
        button.setOnClickListener {
            if(timerIsRunning==false) {
                (activity as MapActivity).drawLine()
                Snackbar.make(view, "score is", Snackbar.LENGTH_LONG).setAction("Action", null)
                    .show()
                timerIsRunning=true
                startTimer()

            }
            else{
                timerIsRunning=false
            }
        }

        return view
    }

    fun startTimer(){
        var seconds:Int =0
        var minuts: Int=0
        var hours: Int =0
        var timerView = view?.findViewById<TextView>(R.id.timerView)
        var startButton = view?.findViewById<Button>(R.id.startTimerButton)
        var runDistance = view?.findViewById<TextView>(R.id.runDistance)


        handler.post(object : Runnable {
                override fun run() {
                    if (activity != null) {
                        if ((activity as MapActivity).hasReachedGoal()) {
                            timerIsRunning == false
                        }
                        if (timerIsRunning) {
                            distance = (activity as MapActivity).calculateTotalDistance()
                            startButton?.text = "stop"
                            seconds++
                            if (seconds == 60) {
                                minuts++
                                if (minuts == 60) {
                                    minuts = 0
                                    hours++
                                }
                                seconds = 0
                            }
                            timerView?.text =
                                hours.toString() + ":" + minuts.toString() + ":" + seconds.toString()
                            runDistance?.text =
                                "total distance ran:" + distance.toString() + " meters"
                            handler.postDelayed(this, 1000)
                        } else {
                            (activity as MapActivity).setTimeSpent(seconds, minuts, hours)
                            (activity as MapActivity).addTimeSpentToUser()
                            startButton?.text = "start"
                            (activity as MapActivity).increaseAmountOfGamesPlayed()
                            (activity as MapActivity).setDistance(distance.toInt())
                            (activity as MapActivity).addDistanceToUser()
                            (activity as MapActivity).addScoreToUser((activity as MapActivity).calculateScore())
                            (activity as MapActivity).addActivity()
                            (activity as MapActivity).isRunning = false
                            (activity as MapActivity).clearMap()
                            distance = 0.0
                        }
                    }
                }
        })
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
        timer = null
        timerIsRunning = false
    }
}




