package com.example.georunner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActivityDataAdapter(private val activities: List<ActivityData>) :
    RecyclerView.Adapter<ActivityDataAdapter.ActivityDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityDataViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity_data, parent, false)

        return ActivityDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityDataViewHolder, position: Int) {
        val activityData = activities[position]
        holder.bind(activityData)
    }

    override fun getItemCount(): Int {
        return activities.size
    }

    class ActivityDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView=itemView.findViewById(R.id.textViewActivity)

        fun bind(activityData: ActivityData) {
            textView.text="session:"+activityData.gameNr.toString()+") "+"distance:"+activityData.distance.toString()+"m / "+"time: "+activityData.timeHours.toString()+":"+activityData.timeMinutes.toString()+":"+activityData.timeSeconds.toString()

        }
    }
}
