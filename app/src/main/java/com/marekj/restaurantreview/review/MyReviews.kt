package com.marekj.restaurantreview.review

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.marekj.restaurantreview.R
import com.marekj.restaurantreview.database.ReviewDatabase
import com.marekj.restaurantreview.database.ReviewEntity
import com.marekj.restaurantreview.recyclerview.MyReviewsAdapter

class MyReviews : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_reviews)
        val user = Firebase.auth.currentUser
        val imageModelArrayList = fillReviews(user!!.uid!!)
        val recyclerView = findViewById<View>(R.id.myReviewsRecycle) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(this) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        val mAdapter = MyReviewsAdapter(imageModelArrayList)
        recyclerView.adapter = mAdapter
    }

    private fun fillReviews(uid: String): ArrayList<ReviewEntity> {
        return ReviewDatabase(this).getReviewsByUID(uid)
    }
}