package com.nasza.terascihampelas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nasza.terascihampelas.R
import com.nasza.terascihampelas.model.Review

class ReviewAdapter(private var reviews: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.text_view_name)
        val reviewTextView: TextView = view.findViewById(R.id.text_view_review)
        val ratingBar: RatingBar = view.findViewById(R.id.rating_bar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.nameTextView.text = review.name
        holder.reviewTextView.text = review.reviewText
        holder.ratingBar.rating = review.rating ?: 0f
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    fun updateReviews(newReviews: List<Review>) {
        reviews = newReviews
        notifyDataSetChanged()
    }
}
