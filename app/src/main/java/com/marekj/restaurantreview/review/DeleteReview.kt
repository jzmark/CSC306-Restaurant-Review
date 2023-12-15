package com.marekj.restaurantreview.review

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marekj.restaurantreview.R
import com.marekj.restaurantreview.database.ReviewDatabase
import com.marekj.restaurantreview.database.ReviewEntity
import com.marekj.restaurantreview.recyclerview.MyReviewsAdapter

class DeleteReview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_delete)
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("id")
            val imageModelArrayList = fillReviews(value!!)
            val recyclerView = findViewById<View>(R.id.deleteReviewRecyclerView) as RecyclerView // Bind to the recyclerview in the layout
            val layoutManager = LinearLayoutManager(this) // Get the layout manager
            recyclerView.layoutManager = layoutManager
            val mAdapter = MyReviewsAdapter(imageModelArrayList)
            recyclerView.adapter = mAdapter
            deleteButtonListener(value)
        }
    }
    private fun fillReviews(id: String): ArrayList<ReviewEntity> {
        Log.w(TAG, id)
        return ReviewDatabase(this).getReviewsByReviewId(id)
    }

    private fun deleteButtonListener(value: String) {
        val button = findViewById<Button>(R.id.removeConfirm)

        button.setOnClickListener {
            val db = ReviewDatabase(this)
            db.removeReview(value)
            val intent = Intent(this, MyReviews::class.java)
            this.startActivity(intent)
            finish()
        }
    }
}