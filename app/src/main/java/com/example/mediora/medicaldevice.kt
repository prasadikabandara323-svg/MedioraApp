package com.example.mediora

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.room.Room
import kotlin.concurrent.thread

class medicaldevice : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicaldevice)


        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "mediora_db_new"
        ).fallbackToDestructiveMigration()
            .build()


        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, PharmacyActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnCart = findViewById<ImageView>(R.id.btnCart)
        btnCart.setOnClickListener {
            val intent = Intent(this, order_details::class.java)
            startActivity(intent)
        }


        val navHome = findViewById<LinearLayout>(R.id.navHome)
        val navPharmacy = findViewById<LinearLayout>(R.id.navPharmacy)
        val navEChanneling = findViewById<LinearLayout>(R.id.navEChanneling)
        val navAccount = findViewById<LinearLayout>(R.id.navSearch)
        val etSearchDevices = findViewById<EditText>(R.id.etSearchDevices)


        navHome.setOnClickListener {

            val intent = Intent(this, home::class.java)
            startActivity(intent)
            finish()
        }

        navPharmacy.setOnClickListener {

            Toast.makeText(this, "You are already on the Pharmacy Page!", Toast.LENGTH_SHORT).show()
        }

        navEChanneling.setOnClickListener {

            val intent = Intent(this, EBookingActivity::class.java)
            startActivity(intent)
            finish()
        }

        navAccount.setOnClickListener {

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }


        findViewById<CardView>(R.id.btnAddBPMonitor).setOnClickListener { saveToDatabase("BP Monitor", 8500.00, "Automatic") }
        findViewById<CardView>(R.id.btnAddGlucoseMeter).setOnClickListener { saveToDatabase("Glucose Meter", 4200.00, "1 Unit") }
        findViewById<CardView>(R.id.btnAddPulseOximeter).setOnClickListener { saveToDatabase("Pulse Oximeter", 3500.00, "1 Unit") }
        findViewById<CardView>(R.id.btnAddThermometer).setOnClickListener { saveToDatabase("Thermometer", 850.00, "Digital") }
        findViewById<CardView>(R.id.btnAddNebulizer).setOnClickListener { saveToDatabase("Nebulizer", 6500.00, "1 Unit") }
        findViewById<CardView>(R.id.btnAddIRThermometer).setOnClickListener { saveToDatabase("IR Thermometer", 2500.00, "Infrared") }
        findViewById<CardView>(R.id.btnAddWeighingScale).setOnClickListener { saveToDatabase("Weighing Scale", 3200.00, "Digital") }
        findViewById<CardView>(R.id.btnAddStethoscope).setOnClickListener { saveToDatabase("Stethoscope", 1500.00, "1 Unit") }
        findViewById<CardView>(R.id.btnAddHeatingPad).setOnClickListener { saveToDatabase("Heating Pad", 1800.00, "1 Unit") }


        val hiddenLayout = findViewById<LinearLayout>(R.id.hidden_layout)
        val btnSeeMore = findViewById<CardView>(R.id.see_more_button)
        val tvSeeMore = findViewById<TextView>(R.id.tvSeeMore)
        val tvArrow = findViewById<TextView>(R.id.tvArrow)

        btnSeeMore.setOnClickListener {
            if (hiddenLayout.visibility == View.GONE) {
                hiddenLayout.visibility = View.VISIBLE
                tvSeeMore.text = "See less"
                tvArrow.text = ""
            } else {
                hiddenLayout.visibility = View.GONE
                tvSeeMore.text = "See more"
                tvArrow.text = ""
            }
        }


        val itemBPMonitor = findViewById<LinearLayout>(R.id.itemBPMonitor)
        val itemGlucoseMeter = findViewById<LinearLayout>(R.id.itemGlucoseMeter)
        val itemPulseOximeter = findViewById<LinearLayout>(R.id.itemPulseOximeter)
        val itemThermometer = findViewById<LinearLayout>(R.id.itemThermometer)
        val itemNebulizer = findViewById<LinearLayout>(R.id.itemNebulizer)
        val itemIRThermometer = findViewById<LinearLayout>(R.id.itemIRThermometer)
        val itemWeighingScale = findViewById<LinearLayout>(R.id.itemWeighingScale)
        val itemStethoscope = findViewById<LinearLayout>(R.id.itemStethoscope)
        val itemHeatingPad = findViewById<LinearLayout>(R.id.itemHeatingPad)

        val itemsList = listOf(itemBPMonitor, itemGlucoseMeter, itemPulseOximeter, itemThermometer, itemNebulizer, itemIRThermometer, itemWeighingScale, itemStethoscope, itemHeatingPad)
        val namesList = listOf("bp monitor", "glucose meter", "pulse oximeter", "thermometer", "nebulizer", "ir thermometer", "weighing scale", "stethoscope", "heating pad")

        etSearchDevices.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase().trim()

                if (query.isNotEmpty()) {
                    hiddenLayout.visibility = View.VISIBLE
                    btnSeeMore.visibility = View.GONE
                } else {
                    hiddenLayout.visibility = View.GONE
                    btnSeeMore.visibility = View.VISIBLE
                    tvSeeMore.text = "See more"
                    tvArrow.text = ""
                }

                for (i in namesList.indices) {
                    if (namesList[i].contains(query)) {
                        itemsList[i].visibility = View.VISIBLE
                    } else {
                        itemsList[i].visibility = View.GONE
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun saveToDatabase(name: String, price: Double, mg: String) {
        thread {
            val cartItem = CartItem(
                medicineName = name,
                medicinePrice = price,
                medicineMg = mg,
                quantity = 1,
                imageResource = ""
            )
            database.cartDao().addToCart(cartItem)

            runOnUiThread {
                Toast.makeText(this, "$name added to cart successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}