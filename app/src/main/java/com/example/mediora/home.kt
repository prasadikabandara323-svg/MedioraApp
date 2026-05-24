package com.example.mediora

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import java.util.Timer
import java.util.TimerTask

class home : AppCompatActivity() {

    private lateinit var promoSlider: ViewPager2
    private lateinit var ivScan: ImageView
    private lateinit var ivMail: ImageView
    private lateinit var ivAccount: ImageView
    private lateinit var btnEmergency: LinearLayout // Emergency button එක සඳහා

    private var handler = Handler(Looper.getMainLooper())
    private var timer: Timer? = null

    private val images = intArrayOf(
        R.drawable.pramo1,
        R.drawable.pramo2,
        R.drawable.pramo3,
        R.drawable.pramo4,

        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 1. Promo Slider
        promoSlider = findViewById(R.id.promoSlider)
        val adapter = PromoAdapter(images)
        promoSlider.adapter = adapter
        startAutoSlide()

        // 2. Scan Icon
        ivScan = findViewById(R.id.btnScan)
        ivScan.setOnClickListener {
            val intent = Intent(this, ScannerActivity::class.java)
            startActivity(intent)
        }

        // 3. Mail Icon (Notification Page එකට)
        ivMail = findViewById(R.id.btnMail)
        ivMail.setOnClickListener {
            val intent = Intent(this, Notification::class.java)
            startActivity(intent)
        }

        // 4. Setting Icon
        ivAccount = findViewById(R.id.btnAccount)
        ivAccount.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // About Us button එක සම්බන්ධ කිරීම
        val btnAboutUs = findViewById<LinearLayout>(R.id.btnAboutUs)
        btnAboutUs.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }


        // Profile button එකේ ආරක්ෂිත බව තහවුරු කිරීම
        // ඔබේ code එක මෙහෙම වෙනස් කරන්න
        val imgHealthProfile = findViewById<ImageView>(R.id.imgHealthProfile)

        imgHealthProfile?.setOnClickListener {
            // 1. මුලින්ම email එක තියෙන variable එකක් හදාගන්න (මේකේ තියෙන්න ඕනේ login වුණු කෙනාගේ email එක)
            val userEmail = "login_වුණු_කෙනාගේ_email_එක"

            // 2. Intent එක හදන්න
            val intent = Intent(this, ProfileActivity::class.java)

            // 3. Email එක අනිවාර්යයෙන්ම දාන්න
            intent.putExtra("USER_EMAIL", userEmail)

            // 4. Activity එක පටන් ගන්න
            startActivity(intent)

        }

        // 5. Emergency Icon
        btnEmergency = findViewById(R.id.btnEmergency)
        btnEmergency.setOnClickListener {
            val phoneNumber = "1990"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            startActivity(intent)
        }
    }

    private fun startAutoSlide() {
        val update = Runnable {
            val nextItem = (promoSlider.currentItem + 1) % images.size
            promoSlider.setCurrentItem(nextItem, true)
        }

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 3000, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}