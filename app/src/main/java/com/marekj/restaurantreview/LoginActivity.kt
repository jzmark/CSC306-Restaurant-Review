package com.marekj.restaurantreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.login_menu)
        val loginButton = findViewById<Button>(R.id.signUp)

        loginButton.setOnClickListener() {

        }

        val signUpButton = findViewById<Button>(R.id.signUp)
        signUpButton.setOnClickListener() {
            val signUpActivity = Intent(this, RegisterActivity::class.java)
            startActivity(signUpActivity)
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //reload()
        }
    }

}