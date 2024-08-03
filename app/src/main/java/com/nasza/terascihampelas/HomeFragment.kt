package com.nasza.terascihampelas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nasza.terascihampelas.adapter.ReviewAdapter
import com.nasza.terascihampelas.model.Review
import com.nasza.terascihampelas.model.ReviewViewModel


class HomeFragment : Fragment() {

    private val reviewViewModel: ReviewViewModel by viewModels()
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Setup ImageView and TextView
        val imageView: ImageView = view.findViewById(R.id.teras_image)
        val descriptionTextView: TextView = view.findViewById(R.id.teras_description)
        descriptionTextView.text = "Teras Cihampelas adalah area pejalan kaki yang berada di atas jalan Cihampelas di Bandung."

        // Setup RecyclerView for reviews
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_reviews)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        reviewAdapter = ReviewAdapter(emptyList())
        recyclerView.adapter = reviewAdapter

        reviewViewModel.allReviews.observe(viewLifecycleOwner) { reviews ->
            reviewAdapter = ReviewAdapter(reviews)
            recyclerView.adapter = reviewAdapter
        }

        // Setup EditText and Button for submitting reviews
        val reviewEditText: EditText = view.findViewById(R.id.edit_text_review)
        val submitButton: Button = view.findViewById(R.id.button_submit_review)
        submitButton.setOnClickListener {
            val reviewText = reviewEditText.text.toString()
            if (reviewText.isNotEmpty()) {
                val review = Review(reviewText = reviewText)
                reviewViewModel.insert(review)
                reviewEditText.text.clear()
            }
        }

        val openMapButton: View = view.findViewById(R.id.openMapButton)
        openMapButton.setOnClickListener {
            openGoogleMaps()
        }

        return view
    }

    private fun openGoogleMaps() {
        val latitude = -6.8929665
        val longitude = 107.6041878
        val uri = "https://www.google.com/maps?q=$latitude,$longitude"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }
}