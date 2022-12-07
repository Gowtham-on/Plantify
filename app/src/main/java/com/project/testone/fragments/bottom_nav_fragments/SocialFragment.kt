package com.project.testone.fragments.bottom_nav_fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.project.testone.R
import kotlinx.android.synthetic.main.fragment_social.*

class SocialFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        firstLayout.setOnClickListener {
            var intent = Intent(requireContext().applicationContext, ChatScreenActivity::class.java)
            intent.putExtra("userName", "Natalia")
            startActivity(intent)
        }

        secondLayout.setOnClickListener {
            var intent = Intent(requireContext().applicationContext, ChatScreenActivity::class.java)
            intent.putExtra("userName", "Joe Agnes")
            startActivity(intent)
        }
        thirdLayout.setOnClickListener {
            var intent = Intent(requireContext().applicationContext, ChatScreenActivity::class.java)
            intent.putExtra("userName", "Hems Dharu")
            startActivity(intent)
        }

        fourthLayout.setOnClickListener {
            var intent = Intent(requireContext().applicationContext, ChatScreenActivity::class.java)
            intent.putExtra("userName", "John")
            startActivity(intent)
        }

        fifthLayout.setOnClickListener {
            var intent = Intent(requireContext().applicationContext, ChatScreenActivity::class.java)
            intent.putExtra("userName", "Sweety Nisha")
            startActivity(intent)
        }
    }
}