package com.project.testone.fragments.side_navbar_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.project.testone.R
import com.project.testone.homeScreen.PlantDetailsActivity
import com.project.testone.paymentAndCart.CheckoutActivity
import kotlinx.android.synthetic.main.action_bar_layout.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false) as LinearLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        firstImage.setOnClickListener {
            val intent = Intent(activity?.applicationContext, PlantDetailsActivity::class.java)
            intent.putExtra("plantNumber", "one")
            startActivity(intent)
        }

        secondImage.setOnClickListener {
            val intent = Intent(activity?.applicationContext, PlantDetailsActivity::class.java)
            intent.putExtra("plantNumber", "two")
            startActivity(intent)
        }

        thirdImage.setOnClickListener {
            val intent = Intent(activity?.applicationContext, PlantDetailsActivity::class.java)
            intent.putExtra("plantNumber", "three")
            startActivity(intent)
        }

        fourthImage.setOnClickListener {
            val intent = Intent(activity?.applicationContext, PlantDetailsActivity::class.java)
            intent.putExtra("plantNumber", "four")
            startActivity(intent)
        }

    }
}