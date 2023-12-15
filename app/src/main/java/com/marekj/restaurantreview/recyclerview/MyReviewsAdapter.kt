package com.marekj.restaurantreview.recyclerview

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marekj.restaurantreview.R
import com.marekj.restaurantreview.RestaurantView
import com.marekj.restaurantreview.database.ReviewEntity

class MyReviewsAdapter (private val imageModelArrayList: MutableList<ReviewEntity>)
    : RecyclerView.Adapter<MyReviewsAdapter.ViewHolder>() {

    /*
     * Inflate our views using the layout defined in row_layout.xml
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.review_row_layout, parent, false)

        return ViewHolder(v)
    }

    /*
     * Bind the data to the child views of the ViewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]

        holder.stars.numStars = info.stars
        holder.username.text = info.username
        holder.location.text = info.location
        holder.review.text = info.review
    }

    /*
     * Get the maximum size of the
     */
    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    /*
     * The parent class that handles layout inflation and child view use
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var stars = itemView.findViewById<RatingBar>(R.id.ratingBarReviewRow)
        var username = itemView.findViewById<TextView>(R.id.usernameReview)
        var location = itemView.findViewById<TextView>(R.id.locationReview)
        var review = itemView.findViewById<TextView>(R.id.review)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {

        }
    }
}




//var intent = Intent(itemView.context, TeamDetail::class.java)
//itemView.context.startActivity(intent)