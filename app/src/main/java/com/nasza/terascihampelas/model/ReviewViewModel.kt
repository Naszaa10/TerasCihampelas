package com.nasza.terascihampelas.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ReviewViewModel : ViewModel() {

    private val repository = ReviewRepository()
    val allReviews: LiveData<List<Review>> = repository.getReviews()

    fun insert(review: Review) {
        repository.addReview(review)
    }
}
