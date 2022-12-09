package com.project.testone.paymentAndCart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.project.testone.R
import kotlinx.android.synthetic.main.action_bar_layout.*
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.action_bar_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        var mrp = intent.getStringExtra("mrp")
        var discount = intent.getStringExtra("discount")
        var total = intent.getStringExtra("total")

        cartBtn.visibility = View.GONE

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