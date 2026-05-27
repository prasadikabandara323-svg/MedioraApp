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

class diabetes_care : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_diabetes_care)


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
        val etSearchDiabetes = findViewById<EditText>(R.id.etSearchDiabetes)


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


        findViewById<CardView>(R.id.btnAddMetformin).setOnClickListener { saveToDatabase("Metformin", 12.50, "500 mg") }
        findViewById<CardView>(R.id.btnAddGliclazide).setOnClickListener { saveToDatabase("Gliclazide", 18.00, "80 mg") }
        findViewById<CardView>(R.id.btnAddGlimepiride).setOnClickListener { saveToDatabase("Glimepiride", 32.00, "3 mg") }
        findViewById<CardView>(R.id.btnAddSitagliptin).setOnClickListener { saveToDatabase("Sitagliptin", 45.00, "50 mg") }
        findViewById<CardView>(R.id.btnAddVildagliptin).setOnClickListener { saveToDatabase("Vildagliptin", 38.00, "50 mg") }
        findViewById<CardView>(R.id.btnAddPioglitazone).setOnClickListener { saveToDatabase("Pioglitazone", 12.50, "15 mg") }
        findViewById<CardView>(R.id.btnAddEmpagliflozin).setOnClickListener { saveToDatabase("Empagliflozin", 85.00, "10 mg") }
        findViewById<CardView>(R.id.btnAddDapagliflozin).setOnClickListener { saveToDatabase("Dapagliflozin", 75.00, "10 mg") }
        findViewById<CardView>(R.id.btnAddLinagliptin).setOnClickListener { saveToDatabase("Linagliptin", 50.00, "5 mg") }


        val hiddenLayout = findViewById<LinearLayout>(R.id.hidden_layout)
        val btnSeeMore = findViewById<CardView>(R.id.see_more_button)
        val tvSeeMore = findViewById<TextView>(R.id.tvSeeMore)
        val tvArrow = findViewById<TextView>(R.id.tvArrow)

        btnSeeMore.setOnClickListener {
            if (hiddenLayout.visibility == View.GONE) {
                hiddenLayout.visibility = View.VISIBLE
                tvSeeMore.text = "See less"
                tvArrow.text = "↑"
            } else {
                hiddenLayout.visibility = View.GONE
                tvSeeMore.text = "See more"
                tvArrow.text = "↓"
            }
        }


        val itemMetformin = findViewById<LinearLayout>(R.id.itemMetformin)
        val itemGliclazide = findViewById<LinearLayout>(R.id.itemGliclazide)
        val itemGlimepiride = findViewById<LinearLayout>(R.id.itemGlimepiride)
        val itemSitagliptin = findViewById<LinearLayout>(R.id.itemSitagliptin)
        val itemVildagliptin = findViewById<LinearLayout>(R.id.itemVildagliptin)
        val itemPioglitazone = findViewById<LinearLayout>(R.id.itemPioglitazone)
        val itemEmpagliflozin = findViewById<LinearLayout>(R.id.itemEmpagliflozin)
        val itemDapagliflozin = findViewById<LinearLayout>(R.id.itemDapagliflozin)
        val itemLinagliptin = findViewById<LinearLayout>(R.id.itemLinagliptin)

        val itemsList = listOf(itemMetformin, itemGliclazide, itemGlimepiride, itemSitagliptin, itemVildagliptin, itemPioglitazone, itemEmpagliflozin, itemDapagliflozin, itemLinagliptin)
        val namesList = listOf("metformin", "gliclazide", "glimepiride", "sitagliptin", "vildagliptin", "pioglitazone", "empagliflozin", "dapagliflozin", "linagliptin")

        etSearchDiabetes.addTextChangedListener(object : TextWatcher {
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
                    tvArrow.text = "↓"
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