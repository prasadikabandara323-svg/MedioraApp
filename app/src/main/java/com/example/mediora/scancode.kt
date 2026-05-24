package com.example.mediora // ඔබගේ ඇප් එකේ නිවැරදි package name එක මෙතැනට දෙන්න

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ScannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scancode) // ඔබගේ XML ගොනුවේ නම

        // 1. Header Back Button
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // පසුපස පිටුවට යාම
        }

        // 2. Bottom Navigation බොත්තම් හඳුනා ගැනීම
        val btnHome = findViewById<LinearLayout>(R.id.navHome)
        val btnPharmacy = findViewById<LinearLayout>(R.id.navPharmacy)
        val btnEChanneling = findViewById<LinearLayout>(R.id.navEChanneling)
        val btnAccount = findViewById<LinearLayout>(R.id.navAccount)

        // 3. Navigation Click Listeners

        // Home බොත්තම සඳහා
        btnHome.setOnClickListener {
            val intent = Intent(this, home::class.java) // ඔබේ Home Activity නම
            startActivity(intent)
            finish()
        }

        // Pharmacy බොත්තම සඳහා


        // 4. QR Code ImageView (අවශ්‍ය නම් පමණක් යොදන්න)
        val imgQrCode = findViewById<ImageView>(R.id.imgQrCode)
        // imgQrCode.setImageResource(R.drawable.qr) // ඔබේ QR image එක මෙහි Load කරන්න
    }
}