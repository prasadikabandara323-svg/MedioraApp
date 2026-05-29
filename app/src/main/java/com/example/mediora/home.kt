package com.example.mediora

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import java.util.Timer
import java.util.TimerTask

class home : AppCompatActivity() {

    private lateinit var promoSlider: ViewPager2
    private lateinit var ivScan: ImageView
    private lateinit var ivMail: ImageView
    private lateinit var ivAccount: ImageView
    private lateinit var btnEmergency: LinearLayout
    private lateinit var btnMenu: ImageView

    private var handler = Handler(Looper.getMainLooper())
    private var timer: Timer? = null

    private val images = intArrayOf(
        R.drawable.pramo1, R.drawable.pramo2, R.drawable.pramo3, R.drawable.pramo4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Menu Icon setup (Custom PopupWindow)
        btnMenu = findViewById(R.id.btnMenu)
        btnMenu.setOnClickListener {
            val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.popup_menu_layout, null, false)
            // උස වැඩි කර ගැනීමට 1300 ලෙස ලබා දී ඇත, අවශ්‍ය නම් මෙය වෙනස් කරන්න
            val popupWindow = PopupWindow(popupView, 300, 1300, true)

            popupWindow.elevation = 10f
            popupWindow.showAsDropDown(btnMenu, 0, 20)

            // අයිතම වලට click listeners
            popupView.findViewById<LinearLayout>(R.id.menu_home).setOnClickListener { startActivity(Intent(this, home::class.java)); popupWindow.dismiss() }
            popupView.findViewById<LinearLayout>(R.id.menu_pharmacy).setOnClickListener { startActivity(Intent(this, PharmacyActivity::class.java)); popupWindow.dismiss() }
            popupView.findViewById<LinearLayout>(R.id.menu_profile).setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)); popupWindow.dismiss() }
            popupView.findViewById<LinearLayout>(R.id.menu_scancode).setOnClickListener { startActivity(Intent(this, ScancodeActivity::class.java)); popupWindow.dismiss() }
            popupView.findViewById<LinearLayout>(R.id.menu_ebooking).setOnClickListener { startActivity(Intent(this, EBookingActivity::class.java)); popupWindow.dismiss() }

            // නිවැරදි කරන ලද M Med, Chat සහ Review Listeners
            popupView.findViewById<LinearLayout>(R.id.menu_mymed).setOnClickListener { startActivity(Intent(this, Mymed::class.java)); popupWindow.dismiss() }
            popupView.findViewById<LinearLayout>(R.id.menu_chat).setOnClickListener { startActivity(Intent(this, ChatPageActivity::class.java)); popupWindow.dismiss() }
            popupView.findViewById<LinearLayout>(R.id.menu_review).setOnClickListener { startActivity(Intent(this, ReviewActivity::class.java)); popupWindow.dismiss() }

            popupView.findViewById<LinearLayout>(R.id.menu_setting).setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)); popupWindow.dismiss() }
            popupView.findViewById<LinearLayout>(R.id.menu_about).setOnClickListener { startActivity(Intent(this, AboutUsActivity::class.java)); popupWindow.dismiss() }

            // Logout බොත්තම - මුළු App එකෙන්ම Logout වීමට
            popupView.findViewById<LinearLayout>(R.id.menu_logout).setOnClickListener {
                Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show()

                // LoginActivity යනු ඔබගේ login page එකේ Activity එකයි
                val intent = Intent(this, LoginActivity::class.java)

                // පැරණි සියලුම Activity ඉවත් කර Login පිටුව පමණක් පෙන්වීමට
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
                finish()
                popupWindow.dismiss()
            }
        }

        // 1. Promo Slider
        promoSlider = findViewById(R.id.promoSlider)
        val adapter = PromoAdapter(images)
        promoSlider.adapter = adapter
        startAutoSlide()

        // 2. Scan Icon
        ivScan = findViewById(R.id.btnScan)
        ivScan.setOnClickListener { startActivity(Intent(this, ScancodeActivity::class.java)) }

        ivMail = findViewById(R.id.btnMail)
        ivMail.setOnClickListener { startActivity(Intent(this, Notification::class.java)) }

        // 4. Setting Icon
        ivAccount = findViewById(R.id.btnAccount)
        ivAccount.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }

        findViewById<LinearLayout>(R.id.btnAboutUs).setOnClickListener { startActivity(Intent(this, AboutUsActivity::class.java)) }
        findViewById<ImageView>(R.id.btnBookAppointment).setOnClickListener { startActivity(Intent(this, EBookingActivity::class.java)) }
        findViewById<ImageView>(R.id.imgMedicationReminder).setOnClickListener { startActivity(Intent(this, PharmacyActivity::class.java)) }
        findViewById<ImageView>(R.id.imgHealthProfile).setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USER_EMAIL", "User's Email")
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.navPharmacy).setOnClickListener { startActivity(Intent(this, PharmacyActivity::class.java)) }
        findViewById<LinearLayout>(R.id.navEChanneling).setOnClickListener { startActivity(Intent(this, EBookingActivity::class.java)) }
        findViewById<LinearLayout>(R.id.navAccount).setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener { Toast.makeText(this, "Already on home", Toast.LENGTH_SHORT).show() }

        // 5. Emergency Icon
        btnEmergency = findViewById(R.id.btnEmergency)
        btnEmergency.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:1990"))
            startActivity(intent)
        }
    }

    private fun startAutoSlide() {
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    val nextItem = (promoSlider.currentItem + 1) % images.size
                    promoSlider.setCurrentItem(nextItem, true)
                }
            }
        }, 3000, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}