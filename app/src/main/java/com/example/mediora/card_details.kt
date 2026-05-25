package com.example.mediora

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CardDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)

        val etCardNumber = findViewById<EditText>(R.id.etCardNumber)
        val etExpiry = findViewById<EditText>(R.id.etExpiry)
        val etCCV = findViewById<EditText>(R.id.etCCV)
        val btnSaveCard = findViewById<Button>(R.id.btnPay)
        val btnConfirmPayment = findViewById<Button>(R.id.btnConfirmPayment)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        btnBack?.setOnClickListener { finish() }

        btnSaveCard.setOnClickListener {
            if (validateFields(etCardNumber, etExpiry, etCCV)) {
                Toast.makeText(this, "Card Saved Successfully!", Toast.LENGTH_SHORT).show()
            }
        }

        btnConfirmPayment.setOnClickListener {
            if (validateFields(etCardNumber, etExpiry, etCCV)) {
                Toast.makeText(this, "Payment Successful!", Toast.LENGTH_LONG).show()

                val intent = Intent(this, home::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }



        findViewById<LinearLayout>(R.id.navHome).setOnClickListener {
            startActivity(Intent(this, home::class.java))
        }

        findViewById<LinearLayout>(R.id.navPharmacy).setOnClickListener {
            startActivity(Intent(this, PharmacyActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.navEChanneling).setOnClickListener {
            startActivity(Intent(this, EBookingActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.navAccount).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }


    private fun validateFields(etCard: EditText, etExp: EditText, etCCV: EditText): Boolean {
        if (etCard.text.toString().isEmpty()) {
            etCard.error = "Required"
            return false
        }
        if (etExp.text.toString().isEmpty()) {
            etExp.error = "Required"
            return false
        }
        if (etCCV.text.toString().isEmpty()) {
            etCCV.error = "Required"
            return false
        }
        return true
    }
}