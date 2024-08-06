package com.nasza.terascihampelas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nasza.terascihampelas.R
import com.nasza.terascihampelas.model.Event

class EventAdapter(
    private val events: List<Event>,
    private val onDeleteClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.event_name)
        private val dateTextView: TextView = itemView.findViewById(R.id.event_date)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.event_description)
        private val deleteButton: View = itemView.findViewById(R.id.btn_hapus)

        fun bind(event: Event) {
            nameTextView.text = event.name
            dateTextView.text = event.date
            descriptionTextView.text = event.description
            deleteButton.setOnClickListener { onDeleteClick(event) }
        }
    }
}
