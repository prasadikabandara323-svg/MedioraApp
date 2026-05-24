package com.example.mediora

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TellUsMoreActivity : AppCompatActivity() {

    private lateinit var etAge: EditText
    private lateinit var spinnerGender: Spinner
    private lateinit var etHeight: EditText
    private lateinit var etWeight: EditText
    private lateinit var spinnerBloodGroup: Spinner
    private lateinit var btnUpdate: Button
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tell_usmore)

        db = AppDatabase.getDatabase(this)

        val name = intent.getStringExtra("NAME") ?: ""
        val email = intent.getStringExtra("USER_EMAIL") ?: ""
        val phone = intent.getStringExtra("PHONE") ?: ""
        val password = intent.getStringExtra("PASSWORD") ?: ""

        etAge = findViewById(R.id.etAge)
        spinnerGender = findViewById(R.id.spinnerGender)
        etHeight = findViewById(R.id.etHeight)
        etWeight = findViewById(R.id.etWeight)
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup)
        btnUpdate = findViewById(R.id.btnUpdate)

        val genderOptions = arrayOf("Select Gender", "Male", "Female", "Other")
        spinnerGender.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genderOptions)

        val bloodGroups = arrayOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
        spinnerBloodGroup.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bloodGroups)

        btnUpdate.setOnClickListener {
            val age = etAge.text.toString()
            val gender = spinnerGender.selectedItem.toString()
            val height = etHeight.text.toString()
            val weight = etWeight.text.toString()
            val bloodGroup = spinnerBloodGroup.selectedItem.toString()

            if (age.isEmpty() || gender == "Select Gender") {
                Toast.makeText(this, "Please enter your age and gender!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val user = db.userDao().getUserByEmail(email)
                if (user != null) {
                    val updatedUser = user.copy(
                        age = age,
                        gender = gender,
                        height = height,
                        weight = weight,
                        bloodGroup = bloodGroup
                    )
                    db.userDao().updateUser(updatedUser)

                    android.util.Log.d("DB_UPDATE", "User updated: ${updatedUser.email} - Age: ${updatedUser.age}")
                } else {
                    android.util.Log.e("DB_UPDATE", "User not found with email: $email")
                }

                withContext(Dispatchers.Main) {
                    // SharedPreferences වල email එක save කිරීම
                    val sharedPreferences = getSharedPreferences("MedioraPrefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putString("LOGGED_USER", email).apply()

                    Toast.makeText(this@TellUsMoreActivity, "Details updated successfully!", Toast.LENGTH_SHORT).show()

                    // දත්ත යාවත්කාලීන වූ පසු Home වෙත යවනු ලැබේ
                    val intent = Intent(this@TellUsMoreActivity, home::class.java)
                    intent.putExtra("USER_EMAIL", email)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}