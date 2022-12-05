package com.project.testone.infoAndLoginScreens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.project.testone.R
import com.project.testone.dataClass.User
import com.project.testone.homeScreen.HomeActivity
import kotlinx.android.synthetic.main.activity_location.*


class LocationActivity : AppCompatActivity() {
    lateinit var mRequestQueue: RequestQueue
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        if (supportActionBar != null) {
            supportActionBar?.hide();
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("Users").child(auth.currentUser!!.uid)

        getData()
        locationLogout.setOnClickListener {
            auth.signOut()
            googleSignInClient.signOut()
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }

        locationNextBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            val state = etState.text.toString()
            val city = etCity.text.toString()
            intent.putExtra("city", city)
            intent.putExtra("state", state)
            addDataToDatabase(auth.currentUser!!.uid, city, state)
            startActivity(intent)
            finish()
        }


    }

    private fun getData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(User::class.java)
                if (value != null) {
                    val intent = Intent(this@LocationActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LocationActivity, "Failed to get data.", Toast.LENGTH_SHORT)
                    .show();

            }
        })
    }

    private fun addDataToDatabase(uid: String, city: String, state: String) {

        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("id", uid)
        hashMap.put("city", city)
        hashMap.put("state", state)

        databaseReference!!.setValue(hashMap)

    }
}