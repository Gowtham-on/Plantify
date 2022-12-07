package com.project.testone.paymentAndCart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.testone.R
import kotlinx.android.synthetic.main.activity_payment.*


class PaymentActivity : AppCompatActivity() {

    val GOOGLE_PAY_PACKAGE_NAME: String = "com.google.android.apps.nbu.paisa.user"
    val GOOGLE_PAY_REQUEST_CODE: Int = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        val dropdown = findViewById<Spinner>(R.id.paymentMethodSpinner)
        val items = arrayOf("Add a banking account", "Google Pay")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        dropdown.adapter = adapter

        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position == 1) {
                    addCardText.visibility = View.GONE
                    linearLayoutOne.visibility = View.GONE
                    lineTwo.visibility = View.GONE
                } else if(position == 0) {
                    addCardText.visibility = View.VISIBLE
                    linearLayoutOne.visibility = View.VISIBLE
                    lineTwo.visibility = View.VISIBLE
                }
            }

        }

        var mrp = intent.getStringExtra("mrp")
        var discount = intent.getStringExtra("discount")
        var total = (mrp?.toInt()?.minus(discount?.toInt()!!)?.plus(20)).toString()
        var address = intent.getStringExtra("address")

        mrpTv.text = "₹${mrp}"
        discountTv.text = "-₹${discount}"
        totalTv.text = "₹${total}"
        addressEt.text = address

        bottomTotalTv.text = "Total ₹${total}"

        finalPayBtn.setOnClickListener {
            var uri: Uri? = Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", "sounderjanani@oksbi")
                .appendQueryParameter("pn", "Gowtham")
                .appendQueryParameter("mc", "1234")
                .appendQueryParameter("tr", "123456789")
                .appendQueryParameter("am", total)
                .appendQueryParameter("cu", "INR")
                .build()

            if (dropdown.selectedItem == "Google Pay") {


                try {
                    var intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(uri)
                    intent.`package` = GOOGLE_PAY_PACKAGE_NAME
                    startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE)
                } catch (e: Exception) {
                    Toast.makeText(this, "App not available", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "This payment method is currently under maintenance, Please select different payment method",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_PAY_REQUEST_CODE) {
            try {
                Toast.makeText(this, data?.getStringExtra("Status"), Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "App not available", Toast.LENGTH_SHORT).show()
            }
        }
    }
}