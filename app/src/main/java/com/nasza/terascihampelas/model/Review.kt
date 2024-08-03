package com.nasza.terascihampelas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review_table")
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val reviewText: String
)