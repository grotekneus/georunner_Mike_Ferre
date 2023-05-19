package com.example.georunner

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.georunner.databinding.ActivityMikeBinding
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var timerIsRunning: Boolean=false

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

        val button = view.findViewById<Button>(R.id.startTimerButton)
        button.setOnClickListener {
            if(timerIsRunning==false) {
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
        var counter = 0
        //var startTime=60
        var startTime=view?.findViewById<EditText>(R.id.setTimeText)?.text.toString().toLongOrNull() ?: 0
        //val startTimeMillis = startTimeEditText?.text.toString().toLongOrNull() ?: 0

        var timerView = view?.findViewById<TextView>(R.id.timerView)
        val button = view?.findViewById<Button>(R.id.startTimerButton)
        object : CountDownTimer((startTime*1000).toLong(),1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerView?.text = counter.toString()
                counter++
                if(timerIsRunning==false){
                    timerView?.text = "timer stopped"
                    cancel()
                }
            /*
                button?.setOnClickListener{
                    timerView?.text = "timer stopped"
                    timerIsRunning=false
                    //addScore(user, calculateScoreTime(counter))//addScore(user, counter+1)
                    cancel()
                }

                 */
            }

            override fun onFinish() {
                timerView?.text = "Finished"
            }
        }.start()
    }



}
