package com.nasza.terascihampelas.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nasza.terascihampelas.database.AppDatabase
import com.nasza.terascihampelas.model.Review
import com.nasza.terascihampelas.model.ReviewRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReviewViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ReviewRepository
    val allReviews: LiveData<List<Review>>

    init {
        val reviewDao = AppDatabase.getDatabase(application).reviewDao()
        repository = ReviewRepository(reviewDao)
        allReviews = repository.allReviews
    }

    fun insert(review: Review) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(review)
    }
}