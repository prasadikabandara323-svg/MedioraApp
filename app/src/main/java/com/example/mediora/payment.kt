package com.example.mediora

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // 1. Credit Card බොත්තම හඳුනා ගැනීම
        val llCreditCard = findViewById<LinearLayout>(R.id.llCreditCard)

        // 2. බොත්තම ක්‍රියාත්මක කිරීම (Click Listener)
        // '?' භාවිතා කරන්නේ බොත්තම නොමැති නම් ඇප් එක Crash වීම වළක්වා ගැනීමටයි
        llCreditCard?.setOnClickListener {
            val intent = Intent(this, CardDetailsActivity::class.java)
            startActivity(intent)
            // ඔබ දෙවරක් startActivity(intent) ලියා තිබුණා, එය එකක් ලෙස ඉවත් කළා
        }
    }
}