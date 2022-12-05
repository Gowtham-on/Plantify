package com.project.testone.paymentAndCart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.testone.R
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        var mrp = intent.getStringExtra("mrp")
        var discount = intent.getStringExtra("discount")
        var total = intent.getStringExtra("total")

        mrpTextView.text = "₹${mrp}"
        discountTextView.text = "-₹${discount}"
        totalAmountTextView.text = (mrp?.toInt()?.minus(discount?.toInt()!!)?.plus(20)).toString()

        proceedToPayBtn.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("mrp", mrp)
            intent.putExtra("discount", discount)
            intent.putExtra("total", total)
            intent.putExtra("address", etAddressName.text.toString())
            startActivity(intent)
        }


    }
}