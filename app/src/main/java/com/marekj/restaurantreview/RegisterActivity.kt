package com.marekj.restaurantreview

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

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
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //reload()
        }
    }
    private fun signUpListener() {
        val signUpBtn = findViewById<Button>(R.id.signUp)
        signUpBtn.setOnClickListener() {
            val firstNameField : String? = findViewById<TextInputEditText>(R.id.nameInputField)
                .text.toString().trim()
            val emailField : String? = findViewById<TextInputEditText>(R.id.emailInputField)
                .text.toString().trim()
            val pass1Field : String? = findViewById<TextInputEditText>(R.id.pass1InputField)
                .text.toString().trim()
            val pass2Field : String? = findViewById<TextInputEditText>(R.id.pass2InputField)
                .text.toString().trim()
            val checkState : Int = checkFields(firstNameField, emailField, pass1Field, pass2Field)
            if (checkState == 0) {
                auth.createUserWithEmailAndPassword(
                    emailField.toString(),
                    pass1Field.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            val profileUpdates = userProfileChangeRequest {
                                displayName = firstNameField
                            }

                            user!!.updateProfile(profileUpdates)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "User profile updated.")
                                    }
                                }
                            val loginActivity = Intent(this, LoginActivity::class.java)
                            startActivity(loginActivity)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            val signUpBtn = findViewById<View>(R.id.signUp)
                            signUpBtn.hideKeyboard()
                            Snackbar.make(signUpBtn, "TEST",
                                Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
    }
    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    //Returns: 0 - no errors, 1 - wrong email format, 2 - not matching passwords, 3 - first name exceeds
    //30 characters, 4 empty field, 5 pass length shorter than 6 characters
    private fun checkFields(firstName : String?, email : String?, pass1 : String?,
                            pass2 : String?) : Int {
        val signUpBtn = findViewById<View>(R.id.signUp)
        if (firstName.isNullOrBlank() || email.isNullOrBlank() ||
            pass1.isNullOrBlank() || pass2.isNullOrBlank()) {
            signUpBtn.hideKeyboard()
            Snackbar.make(signUpBtn, getString(R.string.emptyField),
                Snackbar.LENGTH_SHORT)
                .show()
            return 4
        }
        else if (firstName.length > 30) {
            signUpBtn.hideKeyboard()
            Snackbar.make(signUpBtn, getString(R.string.nameLimitExceeded),
                Snackbar.LENGTH_SHORT)
                .show()
            return 3
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpBtn.hideKeyboard()
            Snackbar.make(signUpBtn, getString(R.string.wrongEmailFormat),
                Snackbar.LENGTH_SHORT)
                .show()
            return 1
        }
        else if (pass1 != pass2) {
            signUpBtn.hideKeyboard()
            Snackbar.make(signUpBtn, getString(R.string.passNoMatch),
                Snackbar.LENGTH_SHORT)
                .show()
            return 2
        }
        else if (pass1.length < 6) {
            signUpBtn.hideKeyboard()
            Snackbar.make(signUpBtn, getString(R.string.passLengthErr),
                Snackbar.LENGTH_SHORT)
                .show()
            return 5
        }
        else {
            return 0
        }
    }

}