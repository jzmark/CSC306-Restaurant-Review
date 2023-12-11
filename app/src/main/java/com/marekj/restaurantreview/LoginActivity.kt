package com.marekj.restaurantreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
//    private val signInLauncher = registerForActivityResult(
//        FirebaseAuthUIActivityResultContract(),
//    ) { res ->
//        this.onSignInResult(res)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_menu)









        val loginButton = findViewById<Button>(R.id.signUp)








        loginButton.setOnClickListener() {
            val menuIntent = Intent(this, RestaurantListActivity::class.java)
            startActivity(menuIntent)
        }
    }
}