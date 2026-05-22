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

class bloodpressure : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bloodpressure)


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
        val etSearchBloodPressure = findViewById<EditText>(R.id.etSearchBloodPressure)

        navHome.setOnClickListener {
            startActivity(Intent(this, PharmacyActivity::class.java))
            finish()
        }
        navPharmacy.setOnClickListener {
            startActivity(Intent(this, PharmacyActivity::class.java))
            finish()
        }
        navEChanneling.setOnClickListener {
            Toast.makeText(this, "E-Channeling page coming soon!", Toast.LENGTH_SHORT).show()
        }
        navAccount.setOnClickListener {
            Toast.makeText(this, "Account profile coming soon!", Toast.LENGTH_SHORT).show()
        }


        findViewById<CardView>(R.id.btnAddAmlodac).setOnClickListener { saveToDatabase("Amlodac", 12.00, "5 mg") }
        findViewById<CardView>(R.id.btnAddAmlong).setOnClickListener { saveToDatabase("Amlong", 18.50, "2.5 mg") }
        findViewById<CardView>(R.id.btnAddCarvedilol).setOnClickListener { saveToDatabase("Carvedilol", 11.00, "6.25 mg") }
        findViewById<CardView>(R.id.btnAddRamipril).setOnClickListener { saveToDatabase("Ramipril", 14.00, "2.5 mg") }
        findViewById<CardView>(R.id.btnAddLosartan).setOnClickListener { saveToDatabase("Losartan", 16.80, "50 mg") }
        findViewById<CardView>(R.id.btnAddBisoprolol).setOnClickListener { saveToDatabase("Bisoprolol", 11.50, "5 mg") }
        findViewById<CardView>(R.id.btnAddIndapamide).setOnClickListener { saveToDatabase("Indapamide", 13.20, "1.5 mg") }
        findViewById<CardView>(R.id.btnAddAtenolol).setOnClickListener { saveToDatabase("Atenolol", 12.90, "50 mg") }
        findViewById<CardView>(R.id.btnAddValsartan).setOnClickListener { saveToDatabase("Valsartan", 19.45, "80 mg") }


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


        val itemAmlodac = findViewById<LinearLayout>(R.id.itemAmlodac)
        val itemAmlong = findViewById<LinearLayout>(R.id.itemAmlong)
        val itemCarvedilol = findViewById<LinearLayout>(R.id.itemCarvedilol)
        val itemRamipril = findViewById<LinearLayout>(R.id.itemRamipril)
        val itemLosartan = findViewById<LinearLayout>(R.id.itemLosartan)
        val itemBisoprolol = findViewById<LinearLayout>(R.id.itemBisoprolol)
        val itemIndapamide = findViewById<LinearLayout>(R.id.itemIndapamide)
        val itemAtenolol = findViewById<LinearLayout>(R.id.itemAtenolol)
        val itemValsartan = findViewById<LinearLayout>(R.id.itemValsartan)

        val itemsList = listOf(itemAmlodac, itemAmlong, itemCarvedilol, itemRamipril, itemLosartan, itemBisoprolol, itemIndapamide, itemAtenolol, itemValsartan)
        val namesList = listOf("amlodac", "amlong", "carvedilol", "ramipril", "losartan", "bisoprolol", "indapamide", "atenolol", "valsartan")

        etSearchBloodPressure.addTextChangedListener(object : TextWatcher {
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