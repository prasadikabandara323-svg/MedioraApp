package com.example.mediora

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.cardview.widget.CardView

class PharmacyActivity : ComponentActivity() {

    private lateinit var categoryLayouts: List<LinearLayout>
    private lateinit var categoryNames: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy)

        val btnBack = findViewById<View>(R.id.btnBack)
        val etSearch = findViewById<EditText>(R.id.etSearch)

        val btnUploadPrescription = findViewById<CardView>(R.id.btnUploadPrescription)
        btnUploadPrescription.setOnClickListener {
            val intent = Intent(this, upload_prescriptions::class.java)
            startActivity(intent)
        }

        // Initialize category layouts
        val btnPainRelief = findViewById<LinearLayout>(R.id.btnPainRelief)
        val btnAntibiotics = findViewById<LinearLayout>(R.id.btnAntibiotics)
        val btnVitamins = findViewById<LinearLayout>(R.id.btnVitamins)
        val btnColdFlu = findViewById<LinearLayout>(R.id.btnColdFlu)
        val btnBabyCare = findViewById<LinearLayout>(R.id.btnBabyCare)
        val btnDiabetesCare = findViewById<LinearLayout>(R.id.btnDiabetesCare)
        val btnBloodPressure = findViewById<LinearLayout>(R.id.btnBloodPressure)
        val btnSkinCare = findViewById<LinearLayout>(R.id.btnSkinCare)
        val btnMedicalDevices = findViewById<LinearLayout>(R.id.btnMedicalDevices)

        // Initialize bottom navigation icons
        val navHome = findViewById<LinearLayout>(R.id.navHome)
        val navPharmacy = findViewById<LinearLayout>(R.id.navPharmacy)
        val navEChanneling = findViewById<LinearLayout>(R.id.navEChanneling)
        val navAccount = findViewById<LinearLayout>(R.id.navSearch)

        btnPainRelief.setOnClickListener {
            startActivity(Intent(this, PainReliefActivity::class.java))
        }

        btnAntibiotics.setOnClickListener {
            startActivity(Intent(this, AntibioticsActivity::class.java))
        }

        btnVitamins.setOnClickListener {
            startActivity(Intent(this, SupplementsActivity::class.java))
        }

        btnColdFlu.setOnClickListener {
            startActivity(Intent(this, ColdFluActivity::class.java))
        }

        btnBabyCare.setOnClickListener {
            startActivity(Intent(this, baby_mother::class.java))
        }

        btnDiabetesCare.setOnClickListener {
            startActivity(Intent(this, diabetes_care::class.java))
        }

        btnBloodPressure.setOnClickListener {
            startActivity(Intent(this, bloodpressure::class.java))
        }

        btnSkinCare.setOnClickListener {
            startActivity(Intent(this, skincare::class.java))
        }

        btnMedicalDevices.setOnClickListener {
            startActivity(Intent(this, medicaldevice::class.java))
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        navHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        navPharmacy.setOnClickListener {
            Toast.makeText(this, "You are already on the Pharmacy Page!", Toast.LENGTH_SHORT).show()
        }


        navEChanneling.setOnClickListener {
            val intent = Intent(this, EBookingActivity::class.java)
            startActivity(intent)
        }
        navAccount.setOnClickListener {
            Toast.makeText(this, "Account profile coming soon!", Toast.LENGTH_SHORT).show()
        }

        categoryLayouts = listOf(
            btnPainRelief, btnAntibiotics, btnVitamins, btnColdFlu,
            btnBabyCare, btnDiabetesCare, btnBloodPressure, btnSkinCare, btnMedicalDevices
        )

        categoryNames = listOf(
            "pain relief", "antibiotics", "vitamins supplement", "cold flu",
            "baby mother care", "diabetes care", "heart blood pressure", "skin care", "medical devices"
        )

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterCategories(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterCategories(query: String) {
        val lowerCaseQuery = query.lowercase().trim()

        for (i in categoryNames.indices) {
            if (categoryNames[i].contains(lowerCaseQuery)) {
                categoryLayouts[i].visibility = View.VISIBLE
            } else {
                categoryLayouts[i].visibility = View.GONE
            }
        }
    }
}