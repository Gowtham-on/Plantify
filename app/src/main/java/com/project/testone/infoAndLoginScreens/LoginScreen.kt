package com.project.testone.infoAndLoginScreens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.testone.R
import kotlinx.android.synthetic.main.activity_login_screen.*

class LoginScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        supportActionBar?.hide();

        auth = FirebaseAuth.getInstance()

        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("Users")


        loginButtonLoginScreen.setOnClickListener {
            progressBarInLogin.visibility = View.VISIBLE
            val emailId = etLoginEmail.text.toString()
            val pass = etLoginPassword.text.toString()
            Log.d("CHK", "from login screen")
            auth.signInWithEmailAndPassword(emailId, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        progressBarInLogin.visibility = View.GONE
                        Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LocationActivity::class.java)
                        intent.putExtra("emailId", emailId)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "invalid email and pass", Toast.LENGTH_SHORT).show()
                    }
                }
        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        googleLoginBtn.setOnClickListener {
            progressBarInLogin.visibility = View.VISIBLE
            signInGoogle()
        }

        signUpButton.setOnClickListener {
            intent = Intent(this, SignupScreen::class.java)
            startActivity(intent)
        }
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
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
//                addDataToDatabase(auth.currentUser!!.uid, "", "")
                progressBarInLogin.visibility = View.GONE
                val intent = Intent(this, LocationActivity::class.java)
                intent.putExtra("userFromGoogle", account.email)
                intent.putExtra("emailIdFromGoogle", account.displayName)
                Toast.makeText(this, "Signed in as ${account.displayName}", Toast.LENGTH_SHORT)
                    .show()
                startActivity(intent)
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addDataToDatabase(uid: String, city: String, state: String) {


        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("id", uid)
        hashMap.put("city", city)
        hashMap.put("state", state)

        databaseReference!!.child("user").setValue(hashMap)

    }
}