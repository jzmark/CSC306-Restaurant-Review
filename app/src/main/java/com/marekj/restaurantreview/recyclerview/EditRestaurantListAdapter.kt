package com.marekj.restaurantreview.recyclerview

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marekj.restaurantreview.R
import com.marekj.restaurantreview.database.RestaurantDatabase
import com.marekj.restaurantreview.database.ReviewEntity
import com.marekj.restaurantreview.review.DetailedMyReview

class EditRestaurantListAdapter(private val imageModelArrayList: MutableList<ReviewEntity>) :
    RecyclerView.Adapter<EditRestaurantListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.edit_restaurant_row, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]
        val db = RestaurantDatabase(holder.username.context)

        holder.stars.numStars = info.stars
        holder.username.text = info.username
        holder.location.text = info.location
        holder.review.text = info.review
        holder.reviewId = info.reviewId
        holder.restaurantName.text = db.getRestaurantById(info.restaurantId.toString()).name
    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var stars = itemView.findViewById<RatingBar>(R.id.ratingBarReviewRow)
        var username = itemView.findViewById<TextView>(R.id.usernameReview)
        var location = itemView.findViewById<TextView>(R.id.locationReview)
        var review = itemView.findViewById<TextView>(R.id.review)
        var reviewId = "-1"
        var restaurantName = itemView.findViewById<TextView>(R.id.restaurantName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            var intent = Intent(itemView.context, DetailedMyReview::class.java)
            intent.putExtra("id", this.reviewId)
            val context = itemView.context as Activity
            context.startActivity(intent)
            context.finish()
        }
    }
}