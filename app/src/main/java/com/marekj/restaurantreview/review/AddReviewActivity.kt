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
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
        val extras = intent.extras
        if (extras != null) {
            value = extras.getString("id")!!
            buttonListener(value)
            val db = RestaurantDatabase(this)
            findViewById<TextView>(R.id.restaurantAddReviewName).text = db.getRestaurantById(value).name
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

    private fun checkFields(stars : Int?, location : String?, review: String?) : Boolean {
        val submitButton = findViewById<View>(R.id.submitButton)
        if (review.isNullOrBlank() || location.isNullOrBlank()) {
            submitButton.hideKeyboard()
            Snackbar.make(submitButton, getString(R.string.emptyField),
                Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        else if (stars == 0) {
            Snackbar.make(submitButton, getString(R.string.noStars),
                Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        else {
            return true
        }
    }
    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }


}