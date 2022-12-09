package com.project.testone.homeScreen

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.project.testone.R
import com.project.testone.dataClass.User
import com.project.testone.paymentAndCart.CartActivity
import kotlinx.android.synthetic.main.action_bar_layout.*
import kotlinx.android.synthetic.main.activity_plant_details.*

class PlantDetailsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private var plantOne = 0
    private var plantTwo = 0
    private var plantThree = 0
    private var plantFour = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_details)


        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.action_bar_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);


        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("Users")


        var plantNumber = intent.getStringExtra("plantNumber")
        getData()
        noOfCount.text = plantOne.toString()


        if (plantNumber == "two") {

            imageDetailsId.setImageResource(R.drawable.image_two_details)
            noOfCount.text = plantTwo.toString()

        } else if (plantNumber == "three") {

            imageDetailsId.setImageResource(R.drawable.image_three_details)
            noOfCount.text = plantThree.toString()

        } else if (plantNumber == "four") {

            imageDetailsId.setImageResource(R.drawable.image_four_details)
            countImg.visibility = View.GONE

            val param1 = blankBackground.layoutParams as ViewGroup.MarginLayoutParams
            param1.topMargin = 965
            blankBackground.layoutParams = param1
            noOfCount.text = plantFour.toString()

            val param2 = reduceCount.layoutParams as ViewGroup.MarginLayoutParams
            param2.topMargin = 955
            reduceCount.layoutParams = param2

            val param3 = increseCount.layoutParams as ViewGroup.MarginLayoutParams
            param3.topMargin = 965
            increseCount.layoutParams = param3

            val param4 = noOfCount.layoutParams as ViewGroup.MarginLayoutParams
            param4.topMargin = 1025
            noOfCount.layoutParams = param4

        }

        increseCount.setOnClickListener {

            if (plantNumber == "two") {
                plantTwo += 1
                noOfCount.text = plantTwo.toString()
            } else if (plantNumber == "three") {
                plantThree += 1
                noOfCount.text = plantThree.toString()
            } else if (plantNumber == "four") {
                plantFour += 1
                noOfCount.text = plantFour.toString()
            } else {
                plantOne += 1
                noOfCount.text = plantOne.toString()
            }
        }

        cartBtn.setOnClickListener {
            var intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        reduceCount.setOnClickListener {
            var count: Int = noOfCount.text.toString().toInt()
            if (count != 0) {
                if (plantNumber == "two") {
                    plantTwo -= 1
                    noOfCount.text = plantTwo.toString()
                } else if (plantNumber == "three") {
                    plantThree -= 1
                    noOfCount.text = plantThree.toString()
                } else if (plantNumber == "four") {
                    plantFour -= 1
                    noOfCount.text = plantFour.toString()
                } else {
                    plantOne -= 1
                    noOfCount.text = plantOne.toString()
                }
            }
        }

        plantDetailBuyNow.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("counts", noOfCount.text)
            startActivity(intent)
        }

        addToCartBtn.setOnClickListener {
            if (plantNumber == "two") {
                addDataToDatabase(plantTwo.toString(), 2)
            } else if (plantNumber == "three") {
                addDataToDatabase(plantThree.toString(), 3)
            } else if (plantNumber == "four") {
                addDataToDatabase(plantFour.toString(), 4)
            } else {
                addDataToDatabase(plantOne.toString(), 1)
            }

            Toast.makeText(
                this,
                "Added to cart",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun addDataToDatabase(value: String, plant: Int) {
        var hashMap: HashMap<String, String> = HashMap()

        if (plant == 1) {
            hashMap.put("plantOne", value)
        } else if (plant == 2) {
            hashMap.put("plantTwo", value)
        } else if (plant == 3) {
            hashMap.put("plantThree", value)
        } else if (plant == 4) {
            hashMap.put("plantFour", value)
        }

        databaseReference!!.updateChildren(hashMap as Map<String, Any>)

    }

    private fun getData() {
        databaseReference = databaseReference!!.child(auth.currentUser!!.uid)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(User::class.java)
                plantOne = value!!.plantOne.toInt()
                plantTwo = value!!.plantTwo.toInt()
                plantThree = value!!.plantThree.toInt()
                plantFour = value!!.plantFour.toInt()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PlantDetailsActivity, "Failed to get data.", Toast.LENGTH_SHORT)
                    .show();

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}