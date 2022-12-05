package com.project.testone.paymentAndCart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.project.testone.R
import com.project.testone.dataClass.User
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private var plantOneCount = 0
    private var plantTwoCount = 0
    private var plantThreeCount = 0
    private var plantFourCount = 0
    private var discount = 0
    private var totalMrp = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("Users").child(auth.currentUser!!.uid)

        getData()

        addBtnOne.setOnClickListener {
            plantOneCount += 1
            increaseData("plantOne", plantOneCount.toString())
        }
        minusBtnOne.setOnClickListener {
            plantOneCount -= 1
            reduceData("plantOne", plantOneCount.toString())
        }

        addBtnTwo.setOnClickListener {
            plantTwoCount += 1
            increaseData("plantTwo", plantTwoCount.toString())
        }
        minusBtnTwo.setOnClickListener {
            plantTwoCount -= 1
            reduceData("plantTwo", plantTwoCount.toString())
        }

        addBtnThree.setOnClickListener {
            plantThreeCount += 1
            increaseData("plantThree", plantThreeCount.toString())
        }
        minusBtnThree.setOnClickListener {
            plantThreeCount -= 1
            reduceData("plantThree", plantThreeCount.toString())
        }

        addBtnFour.setOnClickListener {
            plantFourCount += 1
            increaseData("plantFour", plantFourCount.toString())
        }
        minusBtnFour.setOnClickListener {
            plantFourCount -= 1
            reduceData("plantFour", plantFourCount.toString())
        }

        checkOutCartBtn.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("mrp", "${totalMrp}")
            intent.putExtra("discount", "$discount")
            intent.putExtra("total", "${totalMrp - discount + 20}")
            startActivity(intent)
        }
    }

    private fun getData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(User::class.java)
                plantOneCount = value!!.plantOne.toInt()
                plantTwoCount = value!!.plantTwo.toInt()
                plantThreeCount = value!!.plantThree.toInt()
                plantFourCount = value!!.plantFour.toInt()
                Log.d("CARTACTIVITY_LOG", "countM: ${value!!.plantOne.toInt()}")
                calculatePrice()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CartActivity, "Failed to get data.", Toast.LENGTH_SHORT)
                    .show();
            }
        })
    }

    private fun calculatePrice() {
        totalMrp =
            plantOneCount * 100 + plantTwoCount * 80 + plantThreeCount * 80 + plantFourCount * 250
        discount = plantOneCount * 30 + plantFourCount * 50
        updateUi()
    }

    private fun updateUi() {
        tvMRP.text = "₹${totalMrp}"
        tvDiscount.text = "-₹${discount}"
        tvTotalAmount.text = "${totalMrp - discount + 20}"
        textCountNumberOne.text = plantOneCount.toString()
        textCountNumberTwo.text = plantTwoCount.toString()
        textCountNumberThree.text = plantThreeCount.toString()
        textCountNumberFour.text = plantFourCount.toString()

        if (plantOneCount == 0) {
            relativeOne.visibility = View.GONE
        }
        if (plantTwoCount == 0) {
            relativeTwo.visibility = View.GONE
        }
        if (plantThreeCount == 0) {
            relativeThree.visibility = View.GONE
        }
        if (plantFourCount == 0) {
            relativeFour.visibility = View.GONE
        }

    }

    private fun reduceData(plant: String, plantCount: String) {

        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put(plant, plantCount)

        databaseReference!!.updateChildren(hashMap as Map<String, Any>)
        getData()

    }

    private fun increaseData(plant: String, plantCount: String) {

        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put(plant, plantCount)

        databaseReference!!.updateChildren(hashMap as Map<String, Any>)
        getData()
    }
}

//textCountNumberOne
//addBtnOne
//minusBtnOne