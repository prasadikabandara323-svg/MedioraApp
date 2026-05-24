package com.example.mediora

import android.content.Intent // Intent සඳහා මෙය අනිවාර්යයි
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ඔබේ XML ගොනු නම "activity_settings" නම් එය මෙතැනට දෙන්න
        setContentView(R.layout.activity_setting)

        val sharedPreferences = getSharedPreferences("MedioraPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // 1. Switches සොයා ගැනීම
        val switchShare = findViewById<Switch>(R.id.switchShareData)
        val switchNoti = findViewById<Switch>(R.id.switchNotifications)
        val switchPdf = findViewById<Switch>(R.id.switchPdfExport)
        val switchHistory = findViewById<Switch>(R.id.switchClearHistory)
        val switchDark = findViewById<Switch>(R.id.switchDarkMode)
        val switchLang = findViewById<Switch>(R.id.switchLanguage)

        // 2. TextView බොත්තම් සොයා ගැනීම
        val profileEdit = findViewById<TextView>(R.id.tvProfileEdit)
        val loginSecurity = findViewById<TextView>(R.id.tvLoginSecurity)
        val linkedDevices = findViewById<TextView>(R.id.tvLinkedDevices)

        // 3. කලින් Save කළ අගයන් Load කිරීම
        switchShare.isChecked = sharedPreferences.getBoolean("shareData", true)
        switchNoti.isChecked = sharedPreferences.getBoolean("notifications", true)
        switchPdf.isChecked = sharedPreferences.getBoolean("pdfExport", true)
        switchHistory.isChecked = sharedPreferences.getBoolean("clearHistory", false)
        switchDark.isChecked = sharedPreferences.getBoolean("darkMode", false)
        switchLang.isChecked = sharedPreferences.getBoolean("language", true)

        // 4. TextView බොත්තම් වල ක්‍රියාකාරිත්වය
        profileEdit.setOnClickListener {
            // ProfileActivity වෙත යොමු කිරීම
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        loginSecurity.setOnClickListener {
            Toast.makeText(this, "Opening Security Settings...", Toast.LENGTH_SHORT).show()
            // අවශ්‍ය නම් මෙතැනටත් Intent එකක් දාන්න
        }

        linkedDevices.setOnClickListener {
            Toast.makeText(this, "Opening Linked Devices...", Toast.LENGTH_SHORT).show()
            // අවශ්‍ය නම් මෙතැනටත් Intent එකක් දාන්න
        }

        // 5. Apply Changes බොත්තම
        val btnApply = findViewById<Button>(R.id.btnApply)
        btnApply.setOnClickListener {
            editor.putBoolean("shareData", switchShare.isChecked)
            editor.putBoolean("notifications", switchNoti.isChecked)
            editor.putBoolean("pdfExport", switchPdf.isChecked)
            editor.putBoolean("clearHistory", switchHistory.isChecked)
            editor.putBoolean("darkMode", switchDark.isChecked)
            editor.putBoolean("language", switchLang.isChecked)
            editor.apply()

            // Dark Mode ක්‍රියාත්මක කිරීම
            if (switchDark.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            Toast.makeText(this, "Settings Applied Successfully!", Toast.LENGTH_SHORT).show()
        }

        // 6. Delete Account
        findViewById<TextView>(R.id.btnDeleteAccount).setOnClickListener {
            Toast.makeText(this, "Account deletion triggered", Toast.LENGTH_SHORT).show()
        }
    }
}