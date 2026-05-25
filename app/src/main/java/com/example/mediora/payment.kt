package com.example.mediora

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val btnBack = findViewById<ImageView>(R.id.btnBack) // XML එකේ ID එකට ගැලපෙන පරිදි
        val btnMail = findViewById<ImageView>(R.id.btnMail)
        val btnSaveDetails = findViewById<Button>(R.id.btnSaveDetails)
        val spinnerCountry = findViewById<Spinner>(R.id.countrySpinner) // Drop-down එක
        val llCreditCard = findViewById<LinearLayout>(R.id.llCreditCard)

        btnBack?.setOnClickListener { finish() }

        btnMail?.setOnClickListener {
            startActivity(Intent(this, Notification::class.java))
        }

        val countries = arrayOf("Sri Lanka", "India", "USA", "UK")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
        spinnerCountry?.adapter = adapter

        btnSaveDetails.setOnClickListener {
            Toast.makeText(this, "Details Saved!", Toast.LENGTH_SHORT).show()
        }

        // 6. Card Payment Navigation
        llCreditCard?.setOnClickListener {
            startActivity(Intent(this, CardDetailsActivity::class.java))
        }

        // 7. Bottom Navigation Bar Links
        findViewById<LinearLayout>(R.id.navHome)?.setOnClickListener { startActivity(Intent(this, home::class.java)) }
        findViewById<LinearLayout>(R.id.navPharmacy)?.setOnClickListener { startActivity(Intent(this, PharmacyActivity::class.java)) }
        findViewById<LinearLayout>(R.id.navEChanneling)?.setOnClickListener { startActivity(Intent(this,
            EBookingActivity::class.java)) }
        findViewById<LinearLayout>(R.id.navAccount)?.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }
}