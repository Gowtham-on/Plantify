package com.project.testone.infoAndLoginScreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.testone.R
import kotlinx.android.synthetic.main.activity_second_screen.*

class SecondScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)
        supportActionBar?.hide();

        fabScreenTwo.setOnClickListener {
            intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }

    }
}