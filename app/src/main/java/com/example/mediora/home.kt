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
import android.widget.Toast

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
            val intent = Intent(this, ScancodeActivity::class.java)
            startActivity(intent)
        }

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

        val btnAboutUs = findViewById<LinearLayout>(R.id.btnAboutUs)
        btnAboutUs.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }





        val btnBookAppointment = findViewById<ImageView>(R.id.btnBookAppointment)

        btnBookAppointment.setOnClickListener {
            val intent = Intent(this, EBookingActivity::class.java)
            startActivity(intent)
        }

        // Medication Reminder බොත්තම
        val btnMedicationReminder = findViewById<ImageView>(R.id.imgMedicationReminder)
        btnMedicationReminder.setOnClickListener {
            val intent = Intent(this, PharmacyActivity::class.java)
            startActivity(intent)
        }


        val imgHealthProfile = findViewById<ImageView>(R.id.imgHealthProfile)
        imgHealthProfile?.setOnClickListener {
            val userEmail = "User's Email"

            val intent = Intent(this, ProfileActivity::class.java)

            intent.putExtra("USER_EMAIL", userEmail)

            startActivity(intent)

        }

        val navPharmacy = findViewById<LinearLayout>(R.id.navPharmacy)
        navPharmacy.setOnClickListener {
            val intent = Intent(this, PharmacyActivity::class.java)
            startActivity(intent)
        }

        val navEChanneling = findViewById<LinearLayout>(R.id.navEChanneling)
        navEChanneling.setOnClickListener {
            val intent = Intent(this, EBookingActivity::class.java)
            startActivity(intent)
        }

        val navAccount = findViewById<LinearLayout>(R.id.navAccount)
        navAccount.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java) // හෝ ඔබේ AccountActivity එක
            startActivity(intent)
        }

        val navHome = findViewById<LinearLayout>(R.id.navHome)
        navHome.setOnClickListener {
            // දැනටමත් Home පිටුවේ සිටින නිසා පණිවිඩයක් පෙන්වයි
            Toast.makeText(this, "You are already on the home page", Toast.LENGTH_SHORT).show()
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