package com.marekj.restaurantreview

import RestaurantDatabase
import ReviewDatabase
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RestaurantView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_view)
        val extras = intent.extras
        if (extras != null) {
            fillRestaurantData(extras.getString("id")!!)
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

}