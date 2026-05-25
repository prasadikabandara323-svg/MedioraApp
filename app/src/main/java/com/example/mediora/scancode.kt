package com.example.mediora

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ScancodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scancode)

        // 1. Header Back Button
        findViewById<ImageView>(R.id.btnBack)?.setOnClickListener { finish() }

        findViewById<ImageView>(R.id.btnMail)?.setOnClickListener {

            val intent = Intent(this, Notification::class.java)
            startActivity(intent)
        }



        // 2. Navigation බොත්තම්
        findViewById<LinearLayout>(R.id.navHome)?.setOnClickListener {
            val intent = Intent(this, home::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        findViewById<LinearLayout>(R.id.navPharmacy)?.setOnClickListener {
            startActivity(Intent(this, PharmacyActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.navEChanneling)?.setOnClickListener {
            startActivity(Intent(this, EBookingActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.navAccount)?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}