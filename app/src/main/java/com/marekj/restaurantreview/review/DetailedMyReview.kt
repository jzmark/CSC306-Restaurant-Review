package com.marekj.restaurantreview.review

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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
import com.marekj.restaurantreview.recyclerview.MyReviewsAdapter
import com.marekj.restaurantreview.restaurant.RestaurantListActivity

class DetailedMyReview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_delete)
        drawerListener()
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("id")
            val imageModelArrayList = fillReviews(value!!)
            val recyclerView =
                findViewById<View>(R.id.deleteReviewRecyclerView) as RecyclerView // Bind to the recyclerview in the layout
            val layoutManager = LinearLayoutManager(this) // Get the layout manager
            recyclerView.layoutManager = layoutManager
            val mAdapter = MyReviewsAdapter(imageModelArrayList)
            recyclerView.adapter = mAdapter
            deleteButtonListener(value)
            editButtonListener(value)
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

    private fun editButtonListener(value: String) {
        val button = findViewById<Button>(R.id.editButton)
        button.setOnClickListener {
            var intent = Intent(this, EditReview::class.java)
            intent.putExtra("id", value)
            startActivity(intent)
            finish()
        }
    }

    private fun drawerListener() {
        val headerDrawer = findViewById<NavigationView>(R.id.navigation)
            .getHeaderView(0)
            .findViewById<TextView>(R.id.headerNavDrawer)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val user = Firebase.auth.currentUser
        var name = ""
        user?.let {
            name = it.displayName.toString()
        }
        headerDrawer.text = "Hi " + name + "!"
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }
        val navigationView = findViewById<NavigationView>(R.id.navigation)
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
                val menuIntent = Intent(this, RestaurantListActivity::class.java)
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