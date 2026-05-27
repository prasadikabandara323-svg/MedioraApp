package com.example.mediora

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import kotlin.system.exitProcess

class Notification : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        // 1. UI Components
        val switchAppointment = findViewById<Switch>(R.id.switchAppointment)
        val switchMedicalAlerts = findViewById<Switch>(R.id.switchMedicalAlerts)
        val switchHealthTips = findViewById<Switch>(R.id.switchHealthTips)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val btnBack = findViewById<ImageView>(R.id.btnBack)

        // 2. Bottom Navigation Bar Layouts (XML එකේ ඇති ID වලට අනුව)
        val navHome = findViewById<LinearLayout>(R.id.navHomeLayout)
        val navPharmacy = findViewById<LinearLayout>(R.id.navPharmacyLayout)
        val navChanneling = findViewById<LinearLayout>(R.id.navChannelingLayout)
        val navAccount = findViewById<LinearLayout>(R.id.navSearchLayout)

        // 3. Navigation Click Listeners
        navHome.setOnClickListener {

            val intent = Intent(this, home::class.java)
            startActivity(intent)
            finish()
        }

        navPharmacy.setOnClickListener {

            Toast.makeText(this, "You are already on the Pharmacy Page!", Toast.LENGTH_SHORT).show()
        }

        navChanneling.setOnClickListener {

            val intent = Intent(this, EBookingActivity::class.java)
            startActivity(intent)
            finish()
        }

        navAccount.setOnClickListener {

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 4. Switch Logic
        switchAppointment.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Appointment: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }

        switchMedicalAlerts.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Medical Alerts: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }

        switchHealthTips.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Health Tips: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }

        // 5. Button Actions
        btnLogout.setOnClickListener {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show()
            finishAffinity()
            exitProcess(0)
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}