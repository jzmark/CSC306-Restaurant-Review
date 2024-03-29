package com.marekj.restaurantreview.guest

import android.content.Intent
import com.marekj.restaurantreview.database.RestaurantDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.marekj.restaurantreview.LoginActivity
import com.marekj.restaurantreview.R
import com.marekj.restaurantreview.database.ReviewDatabase
import com.marekj.restaurantreview.database.ReviewEntity
import com.marekj.restaurantreview.recyclerview.ReviewRestaurantListAdapter
import com.marekj.restaurantreview.restaurant.RestaurantListActivity
import com.marekj.restaurantreview.review.AddReviewActivity
import com.marekj.restaurantreview.review.MyReviews

class GuestRestaurantView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_view)
        drawerListener()
        findViewById<Button>(R.id.addReview).visibility = View.INVISIBLE
        val extras = intent.extras
        if (extras != null) {
            fillRestaurantData(extras.getString("id")!!)
            val imageModelArrayList = fillReviews(extras.getString("id")!!)
            val recyclerView =
                findViewById<View>(R.id.restaurantReviewRecycler) as RecyclerView // Bind to the recyclerview in the layout
            val layoutManager = LinearLayoutManager(this) // Get the layout manager
            recyclerView.layoutManager = layoutManager
            val mAdapter = ReviewRestaurantListAdapter(imageModelArrayList)
            recyclerView.adapter = mAdapter
            reviewButtonListener(extras.getString("id")!!)
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

    private fun reviewButtonListener(restaurantId: String) {

    }

    private fun drawerListener() {
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }
        val navigationView = findViewById<NavigationView>(R.id.navigation)
        val menu = navigationView.menu
        menu.findItem(R.id.myReviewsDrawer).setVisible(false)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.close()
            false
            if (menuItem.itemId == R.id.logoutDrawer) {
                val menuIntent = Intent(this, LoginActivity::class.java)
                Firebase.auth.signOut()
                startActivity(menuIntent)
                finish()
                false
            } else if (menuItem.itemId == R.id.myReviewsDrawer) {
                val menuIntent = Intent(this, MyReviews::class.java)
                startActivity(menuIntent)
                finish()
                false
            } else if (menuItem.itemId == R.id.restaurantsDrawer) {
                val menuIntent = Intent(this, GuestRestaurantListActivity::class.java)
                startActivity(menuIntent)
                finish()
                false
            } else {
                val menuIntent = Intent(this, this::class.java)
                startActivity(menuIntent)
                finish()
                false
            }
        }
    }
}