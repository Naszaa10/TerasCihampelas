package com.nasza.terascihampelas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nasza.terascihampelas.R
import com.nasza.terascihampelas.model.Event

class EventAdapter(private val events: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventName: TextView = itemView.findViewById(R.id.event_name)
        private val eventDate: TextView = itemView.findViewById(R.id.event_date)
        private val eventDescription: TextView = itemView.findViewById(R.id.event_description)

        fun bind(event: Event) {
            eventName.text = event.name
            eventDate.text = event.date
            eventDescription.text = event.description
        }
    }
}