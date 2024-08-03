package com.nasza.terascihampelas.model

import androidx.lifecycle.LiveData
import com.nasza.terascihampelas.database.ReviewDao
import com.nasza.terascihampelas.model.Review

class ReviewRepository(private val reviewDao: ReviewDao) {
    val allReviews: LiveData<List<Review>> = reviewDao.getAllReviews()

    suspend fun insert(review: Review) {
        reviewDao.insertReview(review)
    }
}