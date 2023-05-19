package com.example.georunner

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.georunner.room.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.fixedRateTimer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var timerIsRunning: Boolean=false
private lateinit var user:User
private lateinit var handler: Handler
private var timer: Timer? = null

/**
 * A simple [Fragment] subclass.
 * Use the [InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        handler = Handler(Looper.getMainLooper())

        user=(activity as MapActivity).getUser()

        val button = view.findViewById<Button>(R.id.startTimerButton)
        button.setOnClickListener {
            if(timerIsRunning==false) {
                Snackbar.make(view, "score is", Snackbar.LENGTH_LONG).setAction("Action", null)
                    .show()
                timerIsRunning=true

                startTimer()
               //val user= homeActivity.getUser
                //val user = (activity as HomeActivity).getUser()
                (activity as MapActivity).increaseScoreBy10()

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
        var timerView = view?.findViewById<TextView>(R.id.timerView)
        handler.post(object : Runnable {
                override fun run() {
                    if (timerIsRunning) {
                        seconds++
                        if(seconds==60){
                            minuts++
                            seconds=0
                        }
                        timerView?.text = minuts.toString()+":"+seconds.toString()
                        handler.postDelayed(this, 1000)
                    }
                    else{
                        (activity as MapActivity).setTimeSpent(seconds,minuts,0)
                        (activity as MapActivity).addTimeSpentToUser()
                    }
                }
        })
    }

    override fun onPause() {
        super.onPause()
        //timer?.cancel()
        //timer = null
        //(activity as MapActivity).

        timerIsRunning = false

    }

}




