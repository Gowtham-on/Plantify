package com.project.testone.fragments.bottom_nav_fragments

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
            Toast.makeText(context?.applicationContext, "No user called ${userNameOne.text}", Toast.LENGTH_SHORT).show()
        }

        secondLayout.setOnClickListener {
            Toast.makeText(context?.applicationContext, "No user called ${userNameTwo.text}", Toast.LENGTH_SHORT).show()
        }
        thirdLayout.setOnClickListener {
            Toast.makeText(context?.applicationContext, "No user called ${userNameThree.text}", Toast.LENGTH_SHORT).show()
        }

        fourthLayout.setOnClickListener {
            Toast.makeText(context?.applicationContext, "No user called ${userNameFour.text}", Toast.LENGTH_SHORT).show()
        }

        fifthLayout.setOnClickListener {
            Toast.makeText(context?.applicationContext, "No user called ${userNameFive.text}", Toast.LENGTH_SHORT).show()
        }
    }
}