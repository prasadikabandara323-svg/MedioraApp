package com.example.mediora

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CardDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details) // ඔබේ xml file එකේ නම මෙතැනට දමන්න

        // Views හඳුනා ගැනීම
        val etCardNumber = findViewById<EditText>(R.id.etCardNumber)
        val etExpiry = findViewById<EditText>(R.id.etExpiry)
        val etCCV = findViewById<EditText>(R.id.etCCV)

        val btnSaveCard = findViewById<Button>(R.id.btnPay)
        val btnConfirmPayment = findViewById<Button>(R.id.btnConfirmPayment)

        // Save Card බොත්තම ක්‍රියාත්මක කිරීම
        btnSaveCard.setOnClickListener {
            val cardNumber = etCardNumber.text.toString()

            if (cardNumber.isNotEmpty()) {
                Toast.makeText(this, "Card Saved Successfully!", Toast.LENGTH_SHORT).show()
                // මෙතනට දත්ත save කරන logic එක ලියන්න
            } else {
                etCardNumber.error = "Please enter card number"
            }
        }

        // Confirm Payment බොත්තම ක්‍රියාත්මක කිරීම
        btnConfirmPayment.setOnClickListener {
            val cardNumber = etCardNumber.text.toString()
            val expiry = etExpiry.text.toString()
            val ccv = etCCV.text.toString()

            if (cardNumber.isNotEmpty() && expiry.isNotEmpty() && ccv.isNotEmpty()) {
                Toast.makeText(this, "Processing Payment...", Toast.LENGTH_SHORT).show()
                // මෙතනට payment gateway API එකේ logic එක ලියන්න
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}