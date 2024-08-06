package com.nasza.terascihampelas.model

import com.google.firebase.database.FirebaseDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ReviewRepository {

    private val database = FirebaseDatabase.getInstance().getReference("reviews")

    fun addReview(review: Review) {
        val reviewId = database.push().key ?: return
        review.id = reviewId
        database.child(reviewId).setValue(review)
    }

    fun getReviews(): LiveData<List<Review>> {
        val reviewsLiveData = MutableLiveData<List<Review>>()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val reviews = snapshot.children.mapNotNull { it.getValue(Review::class.java) }
                reviewsLiveData.value = reviews
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
        return reviewsLiveData
    }
}
