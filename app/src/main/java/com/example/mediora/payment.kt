package com.example.mediora

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)


        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnMail = findViewById<ImageView>(R.id.btnMail)
        val btnSaveDetails = findViewById<Button>(R.id.btnSaveDetails)
        val spinnerCountry = findViewById<Spinner>(R.id.countrySpinner)
        val llCreditCard = findViewById<LinearLayout>(R.id.llCreditCard)


        val etPatientName = findViewById<EditText>(R.id.etPatientName)
        val etPatientPhone = findViewById<EditText>(R.id.etPatientPhone)
        val etServiceDetails = findViewById<EditText>(R.id.etServiceDetails)
        val etCharges = findViewById<EditText>(R.id.etCharges)
        val etTotalAmount = findViewById<EditText>(R.id.etTotalAmount)


        val doctorName = intent.getStringExtra("DOCTOR_NAME") ?: ""
        val doctorFee = intent.getStringExtra("DOCTOR_FEE") ?: ""
        val patientName = intent.getStringExtra("PATIENT_NAME") ?: ""
        val contactNumber = intent.getStringExtra("CONTACT_NUMBER") ?: ""


        etPatientName?.setText(patientName)
        etPatientPhone?.setText(contactNumber)
        etServiceDetails?.setText("Channeling - Dr. $doctorName")
        etCharges?.setText("RS: $doctorFee")
        etTotalAmount?.setText("RS: $doctorFee")

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
        findViewById<LinearLayout>(R.id.navEChanneling)?.setOnClickListener { startActivity(Intent(this, EBookingActivity::class.java)) }
        findViewById<LinearLayout>(R.id.navAccount)?.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }
}