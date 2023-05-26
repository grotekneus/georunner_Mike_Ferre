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
        // Bind the activityData object to the views in the ViewHolder
        holder.bind(activityData)
    }

    override fun getItemCount(): Int {
        return activities.size
    }

    class ActivityDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView=itemView.findViewById(R.id.textViewActivity)

        // Declare and initialize the views in the ViewHolder
        // Bind the data to the views in the bind() method
        // Example:
        // private val gameNrTextView: TextView = itemView.findViewById(R.id.gameNrTextView)
        // private val distanceTextView: TextView = itemView.findViewById(R.id.distanceTextView)
        // private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)

        fun bind(activityData: ActivityData) {
            textView.text="session:"+activityData.gameNr.toString()+") "+"distance:"+activityData.distance.toString()+"m / "+"time: "+activityData.timeHours.toString()+":"+activityData.timeMinutes.toString()+":"+activityData.timeSeconds.toString()
            // Bind the activityData object to the views in the ViewHolder
            // Example:
            // gameNrTextView.text = activityData.gameNr.toString()
            // distanceTextView.text = activityData.distance.toString()
            // timeTextView.text = "${activityData.timeHours}h ${activityData.timeSeconds}s"
        }
    }
    /*
    val currentTodoItem = items[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.txtTodoTitle).text = currentTodoItem.title
            findViewById<CheckBox>(R.id.chkTodoDone).isChecked = currentTodoItem.isDone
        }
     */
}

/*
package be.kuleuven.recyclerview

class TodoAdapter(val items: List<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    // this creates the needed ViewHolder class that links our layout XML to our viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        // don't forget to set attachToRoot to false, otherwise it will crash!
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        // bind the data to our items: set the todo text view text and checked state accordingly
        val currentTodoItem = items[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.txtTodoTitle).text = currentTodoItem.title
            findViewById<CheckBox>(R.id.chkTodoDone).isChecked = currentTodoItem.isDone
        }
    }

    override fun getItemCount(): Int = items.size
}
 */
