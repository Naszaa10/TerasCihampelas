package com.nasza.terascihampelas.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nasza.terascihampelas.model.Review

@Dao
interface ReviewDao {
    @Query("SELECT * FROM review_table ORDER BY id DESC")
    fun getAllReviews(): LiveData<List<Review>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReview(review: Review)
}