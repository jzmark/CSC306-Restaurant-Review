package com.marekj.restaurantreview

import android.content.ContentValues.TAG
import com.marekj.restaurantreview.database.RestaurantDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marekj.restaurantreview.database.ReviewDatabase
import com.marekj.restaurantreview.database.ReviewEntity
import com.marekj.restaurantreview.recyclerview.RecyclerViewModel
import com.marekj.restaurantreview.recyclerview.RestaurantListAdapter
import com.marekj.restaurantreview.recyclerview.ReviewRestaurantListAdapter

class RestaurantView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_view)
        val extras = intent.extras
        if (extras != null) {
            fillRestaurantData(extras.getString("id")!!)
            val imageModelArrayList = fillReviews(extras.getString("id")!!)
            val recyclerView = findViewById<View>(R.id.restaurantReviewRecycler) as RecyclerView // Bind to the recyclerview in the layout
            val layoutManager = LinearLayoutManager(this) // Get the layout manager
            recyclerView.layoutManager = layoutManager
            Log.w(TAG, imageModelArrayList.size.toString())
            val mAdapter = ReviewRestaurantListAdapter(imageModelArrayList)
            recyclerView.adapter = mAdapter
        }
    }

    private fun fillRestaurantData(extras: String) {
        val restaurantDatabase = RestaurantDatabase(this)
        val restaurantData = restaurantDatabase.getRestaurantById(extras!!)

        findViewById<TextView>(R.id.restaurantViewName).text = restaurantData.name
        findViewById<TextView>(R.id.restaurantViewDescription).text = restaurantData.description
        findViewById<TextView>(R.id.restaurantViewAddress).text = restaurantData.address
        val drawableId = resources.getIdentifier(restaurantData.imageFile, "drawable", packageName)
        findViewById<ImageView>(R.id.restaurantViewPicture).setImageResource(drawableId)
    }

    private fun fillReviews(extras: String): ArrayList<ReviewEntity> {
        return ReviewDatabase(this).getReviewsByRestaurantId(extras)
    }
}