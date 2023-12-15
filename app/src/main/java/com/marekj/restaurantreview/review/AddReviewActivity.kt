package com.marekj.restaurantreview.review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.marekj.restaurantreview.LoginActivity
import com.marekj.restaurantreview.R
import com.marekj.restaurantreview.restaurant.RestaurantListActivity
import com.marekj.restaurantreview.database.RestaurantDatabase
import com.marekj.restaurantreview.database.ReviewDatabase
import com.marekj.restaurantreview.database.ReviewEntity

class AddReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var value = ""
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_review)
        drawerListener()
        val extras = intent.extras
        if (extras != null) {
            value = extras.getString("id")!!
            buttonListener(value)
            val db = RestaurantDatabase(this)
            findViewById<TextView>(R.id.restaurantAddReviewName).text =
                db.getRestaurantById(value).name
        }
    }

    private fun buttonListener(value: String) {
        val button = findViewById<Button>(R.id.submitButton)
        button.setOnClickListener {
            val stars = findViewById<RatingBar>(R.id.ratingBar).rating.toInt()
            val location = findViewById<TextInputEditText>(R.id.locationText).text.toString()
            val review = findViewById<TextInputEditText>(R.id.addReviewText).text.toString()
            val auth = Firebase.auth
            val db = ReviewDatabase(this)
            if (checkFields(stars, location, review)) {
                db.addReview(
                    ReviewEntity(
                        "-1",
                        auth.currentUser!!.displayName!!,
                        auth.currentUser!!.uid,
                        value.toInt(),
                        review,
                        stars,
                        location
                    )
                )
                val intent = Intent(this, RestaurantListActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun checkFields(stars: Int?, location: String?, review: String?): Boolean {
        val submitButton = findViewById<View>(R.id.submitButton)
        if (review.isNullOrBlank() || location.isNullOrBlank()) {
            submitButton.hideKeyboard()
            Snackbar.make(
                submitButton, getString(R.string.emptyField),
                Snackbar.LENGTH_SHORT
            )
                .show()
            return false
        } else if (stars == 0) {
            submitButton.hideKeyboard()
            Snackbar.make(
                submitButton, getString(R.string.noStars),
                Snackbar.LENGTH_SHORT
            )
                .show()
            return false
        } else {
            return true
        }
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
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