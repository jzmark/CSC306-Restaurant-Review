package com.marekj.restaurantreview

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.login_menu)

        loginListener()

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

    private fun loginListener() {
        val loginButton = findViewById<Button>(R.id.logIn)
        loginButton.setOnClickListener() {
            val emailText : String? = findViewById<TextInputEditText>(R.id.emailInputField)
                .text.toString()
            val passText : String? = findViewById<TextInputEditText>(R.id.passwordInputField)
                .text.toString()
            val check = checkFields(emailText, passText)
            if (check == 0) {
                auth.signInWithEmailAndPassword(emailText.toString(), passText.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            val restaurantListActivity = Intent(this,
                                RestaurantListActivity::class.java)
                            startActivity(restaurantListActivity)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            loginButton.hideKeyboard()
                            Snackbar.make(loginButton, getString(R.string.incorrectCredentials),
                                Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
    }

    //returns: 1 if any field is blank, 2 if email is not of correct format, 0 if test passed
    private fun checkFields(email : String?, pass : String?) : Int {
        val signUpBtn = findViewById<View>(R.id.logIn)
        if (email.isNullOrBlank() || pass.isNullOrBlank()) {
            signUpBtn.hideKeyboard()
            Snackbar.make(signUpBtn, getString(R.string.emptyField),
                Snackbar.LENGTH_SHORT)
                .show()
            return 1
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpBtn.hideKeyboard()
            Snackbar.make(signUpBtn, getString(R.string.wrongEmailFormat),
                Snackbar.LENGTH_SHORT)
                .show()
            return 2
        }
        else {
            return 0
        }
    }
    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }


}