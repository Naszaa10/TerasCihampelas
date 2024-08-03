package com.nasza.terascihampelas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nasza.terascihampelas.R
import com.nasza.terascihampelas.model.Event
import com.nasza.terascihampelas.adapter.EventAdapter

class EventFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val events = listOf(
        Event(1, "Music Festival", "2024-08-01", "Enjoy live music performances from top bands."),
        Event(2, "Art Exhibition", "2024-08-05", "Discover amazing artworks by local artists."),
        // Add more events here
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = EventAdapter(events)
    }
}