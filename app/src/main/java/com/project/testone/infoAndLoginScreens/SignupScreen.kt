package com.project.testone.infoAndLoginScreens

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.testone.R
import kotlinx.android.synthetic.main.activity_signup_screen.*
import com.google.firebase.ktx.Firebase
import com.project.testone.dataClass.User
import kotlin.math.sign

class SignupScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_screen)
        supportActionBar?.hide()

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        signUpGoogleLogin.setOnClickListener {
            progressBarInSignUP.visibility = View.VISIBLE
            googleSignUp()
        }


        signUp.setOnClickListener {
            progressBarInSignUP.visibility = View.VISIBLE
            registerUser()
        }

        loginButton.setOnClickListener {
            intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser() {
        if (etSignUpUserName.text.isEmpty()) {
            etSignUpUserName.error = "Invalid UserName"
            etSignUpUserName.requestFocus()
        } else if (etSignUpMailId.text.isEmpty()) {
            etSignUpMailId.error = "Invalid EmailId"
            etSignUpMailId.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(etSignUpMailId.text).matches()) {
            etSignUpMailId.error = "Please enter valid EmailId"
            etSignUpMailId.requestFocus()
        } else if (etSignUpPassword.text.isEmpty()) {
            etSignUpPassword.error = "Invalid Password"
            etSignUpPassword.requestFocus()
        } else if (etSignUpConfirmPassword.text.isEmpty()) {
            etSignUpConfirmPassword.error = "Confirm Password"
            etSignUpConfirmPassword.requestFocus()
        } else if (etSignUpConfirmPassword.text.equals(etSignUpPassword.text)) {
            etSignUpConfirmPassword.error = "Passwords doesnt match"
            etSignUpConfirmPassword.requestFocus()
        }


        val emailId = etSignUpMailId.text.toString()
        val password = etSignUpPassword.text.toString()


        auth.createUserWithEmailAndPassword(
            emailId, password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                progressBarInSignUP.visibility = View.GONE
                val intent = Intent(this, LocationActivity::class.java)
                startActivity(intent)
            } else {
                progressBarInSignUP.visibility = View.GONE
                Toast.makeText(this, "Error signing in, Please try again", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun googleSignUp() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
        Log.d("chk", "in googleSignUp()")
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount = task.result

            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                progressBarInSignUP.visibility = View.GONE
                val intent = Intent(this, LocationActivity::class.java)
                Toast.makeText(this, "Signed in as ${account.displayName}", Toast.LENGTH_SHORT)
                    .show()
                startActivity(intent)
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}