package com.marekj.restaurantreview

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.register_menu)
        signUpListener()

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //reload()
        }
    }

    private fun signUpListener() {
        val signUpBtn = findViewById<Button>(R.id.signUp)
        signUpBtn.setOnClickListener() {
            val usernameField: String? = findViewById<TextInputEditText>(R.id.usernameField)
                .text.toString().trim()
            val emailField: String? = findViewById<TextInputEditText>(R.id.emailInputField)
                .text.toString().trim()
            val pass1Field: String? = findViewById<TextInputEditText>(R.id.pass1InputField)
                .text.toString().trim()
            val pass2Field: String? = findViewById<TextInputEditText>(R.id.pass2InputField)
                .text.toString().trim()
            val checkState: Int = checkFields(usernameField, emailField, pass1Field, pass2Field)
            if (checkState == 0) {
                auth.createUserWithEmailAndPassword(
                    emailField!!,
                    pass1Field!!
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = Firebase.auth.currentUser
                            val profileUpdates = userProfileChangeRequest {
                                displayName = usernameField
                            }
                            user!!.updateProfile(profileUpdates)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "User profile updated.")
                                        val loginActivity = Intent(this, LoginActivity::class.java)
                                        startActivity(loginActivity)
                                        finish()
                                    }
                                }
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            val signUpBtn = findViewById<View>(R.id.signUp)
                            signUpBtn.hideKeyboard()
                            Snackbar.make(
                                signUpBtn, getString(R.string.emailExists),
                                Snackbar.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
            }
        }
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    //Returns: 0 - no errors, 1 - wrong email format, 2 - not matching passwords, 3 - username exceeds
    //10 characters or smaller than 4, 4 empty field, 5 pass length shorter than 6 characters
    private fun checkFields(
        username: String?, email: String?, pass1: String?,
        pass2: String?
    ): Int {
        val signUpBtn = findViewById<View>(R.id.signUp)
        if (username.isNullOrBlank() || email.isNullOrBlank() ||
            pass1.isNullOrBlank() || pass2.isNullOrBlank()
        ) {
            signUpBtn.hideKeyboard()
            Snackbar.make(
                signUpBtn, getString(R.string.emptyField),
                Snackbar.LENGTH_SHORT
            )
                .show()
            return 4
        } else if (username.length > 10 || username.length < 4) {
            signUpBtn.hideKeyboard()
            Snackbar.make(
                signUpBtn, getString(R.string.nameLimitExceeded),
                Snackbar.LENGTH_SHORT
            )
                .show()
            return 3
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpBtn.hideKeyboard()
            Snackbar.make(
                signUpBtn, getString(R.string.wrongEmailFormat),
                Snackbar.LENGTH_SHORT
            )
                .show()
            return 1
        } else if (pass1 != pass2) {
            signUpBtn.hideKeyboard()
            Snackbar.make(
                signUpBtn, getString(R.string.passNoMatch),
                Snackbar.LENGTH_SHORT
            )
                .show()
            return 2
        } else if (pass1.length < 6) {
            signUpBtn.hideKeyboard()
            Snackbar.make(
                signUpBtn, getString(R.string.passLengthErr),
                Snackbar.LENGTH_SHORT
            )
                .show()
            return 5
        } else {
            return 0
        }
    }

}