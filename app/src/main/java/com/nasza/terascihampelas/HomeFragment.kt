package com.nasza.terascihampelas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.nasza.terascihampelas.adapter.ReviewAdapter
import com.nasza.terascihampelas.model.Review
import com.nasza.terascihampelas.model.ReviewViewModel
import de.hdodenhof.circleimageview.BuildConfig
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class HomeFragment : Fragment() {

    private val reviewViewModel: ReviewViewModel by viewModels()
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var mapView: MapView
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Setup ImageView and TextView
        val imageView: ImageView = view.findViewById(R.id.teras_image)
        val descriptionTextView: TextView = view.findViewById(R.id.teras_description)

        val btnTik: ImageButton = view.findViewById(R.id.btn_tiktok)
        btnTik.setOnClickListener {
            val uri = Uri.parse("https://vm.tiktok.com/ZSYo7QdaK/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        val btnIG: ImageButton = view.findViewById(R.id.btn_ig)
        btnIG.setOnClickListener {
            val uri = Uri.parse("https://www.instagram.com/teras_cihampelas/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        // Setup RecyclerView for reviews
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_reviews)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        reviewAdapter = ReviewAdapter(emptyList())
        recyclerView.adapter = reviewAdapter

        reviewViewModel.allReviews.observe(viewLifecycleOwner) { reviews ->
            reviewAdapter.updateReviews(reviews)
        }

        // Setup EditText and Button for submitting reviews
        val nameEditText: EditText = view.findViewById(R.id.edit_text_name)
        val reviewEditText: EditText = view.findViewById(R.id.edit_text_review)
        val ratingBar: RatingBar = view.findViewById(R.id.rating_bar)
        val submitButton: Button = view.findViewById(R.id.button_submit_review)
        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val reviewText = reviewEditText.text.toString()
            val rating = ratingBar.rating
            if (name.isNotEmpty() && reviewText.isNotEmpty()) {
                val review = Review(name = name, reviewText = reviewText, rating = rating)
                reviewViewModel.insert(review)
                nameEditText.text.clear()
                reviewEditText.text.clear()
                ratingBar.rating = 0f
            }
        }

        // Initialize Map
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        mapView = view.findViewById(R.id.map)
        mapView.setMultiTouchControls(true)

        // Set initial map center and zoom level
        val mapController = mapView.controller
        mapController.setZoom(15.0)
        val startPoint = GeoPoint(-6.8929665, 107.6041878)
        mapController.setCenter(startPoint)

        // Add marker to map
        val startMarker = Marker(mapView)
        startMarker.position = startPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        startMarker.title = "Lokasi Teras Cihampelas"
        mapView.overlays.add(startMarker)

        // Google Maps button
        val btnOpenMaps: Button = view.findViewById(R.id.btn_open_maps)
        btnOpenMaps.setOnClickListener {
            val uri = Uri.parse("https://www.google.com/maps/place/Teras+Cihampelas+(Skywalk+Cihampelas)/@-6.8929611,107.601606,17z/data=!3m1!4b1!4m6!3m5!1s0x2e68e65b7f704c5b:0xa7c370b143afa427!8m2!3d-6.8929664!4d107.6041863!16s%2Fg%2F11cjh_yw_m?entry=ttu")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}