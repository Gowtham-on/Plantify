package com.project.testone.infoAndLoginScreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.project.testone.R
import com.project.testone.homeScreen.HomeActivity
import kotlinx.android.synthetic.main.activity_first_screen.*

class FirstScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)
        supportActionBar?.hide();

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null) {
            Log.d("CHK", "from firstScreen")
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        fabScreenOne.setOnClickListener {
            intent = Intent(this, SecondScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}