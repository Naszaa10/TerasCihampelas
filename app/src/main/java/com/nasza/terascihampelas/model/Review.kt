package com.nasza.terascihampelas.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Review(
    var id: String? = "",
    var name: String? = "",
    var reviewText: String? = "",
    var rating: Float? = 0f
)