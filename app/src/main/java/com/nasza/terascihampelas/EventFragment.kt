package com.nasza.terascihampelas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nasza.terascihampelas.adapter.EventAdapter
import com.nasza.terascihampelas.model.Event

class EventFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addEventButton: Button
    private val events = mutableListOf<Event>()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("events")
    private lateinit var adapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        addEventButton = view.findViewById(R.id.add_event_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = EventAdapter(events, ::onDeleteClick)
        recyclerView.adapter = adapter

        addEventButton.setOnClickListener {
            showAddEventDialog()
        }

        loadEvents()
    }

    private fun showAddEventDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Tambah Event")

        val view = layoutInflater.inflate(R.layout.dialog_add_event, null)
        val nameEditText = view.findViewById<EditText>(R.id.edit_event_name)
        val dateEditText = view.findViewById<EditText>(R.id.edit_event_date)
        val descriptionEditText = view.findViewById<EditText>(R.id.edit_event_description)

        builder.setView(view)
        builder.setPositiveButton("Tambah") { _, _ ->
            val newEvent = Event(
                id = database.push().key ?: "",
                name = nameEditText.text.toString(),
                date = dateEditText.text.toString(),
                description = descriptionEditText.text.toString()
            )
            addEventToDatabase(newEvent)
        }
        builder.setNegativeButton("Batal", null)
        builder.show()
    }

    private fun addEventToDatabase(event: Event) {
        database.child(event.id).setValue(event)
            .addOnSuccessListener {
                // Data successfully added to database
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    private fun loadEvents() {
        database.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val event = snapshot.getValue(Event::class.java)
                if (event != null) {
                    // Check if the event is already in the list to avoid duplicates
                    if (events.none { it.id == event.id }) {
                        events.add(event)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val event = snapshot.getValue(Event::class.java)
                if (event != null) {
                    val index = events.indexOfFirst { it.id == event.id }
                    if (index != -1) {
                        events[index] = event
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val event = snapshot.getValue(Event::class.java)
                if (event != null) {
                    events.removeAll { it.id == event.id }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Handle if needed, e.g., if your data is ordered
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })
    }

    private fun onDeleteClick(event: Event) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Hapus Event")
            .setMessage("Apakah Anda yakin ingin menghapus event ini?")
            .setPositiveButton("Hapus") { _, _ ->
                deleteEventFromDatabase(event)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun deleteEventFromDatabase(event: Event) {
        database.child(event.id).removeValue()
            .addOnSuccessListener {
                events.remove(event)
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
