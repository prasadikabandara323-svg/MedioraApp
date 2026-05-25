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

class skincare : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_skincare)


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
        val etSearchSkinCare = findViewById<EditText>(R.id.etSearchSkinCare)

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


        findViewById<CardView>(R.id.btnAddCetaphil).setOnClickListener { saveToDatabase("Cetaphil Cleanser", 4650.00, "125 ml") }
        findViewById<CardView>(R.id.btnAddNeutrogena).setOnClickListener { saveToDatabase("Neutrogena Gel", 5250.00, "50 g") }
        findViewById<CardView>(R.id.btnAddCerave).setOnClickListener { saveToDatabase("CeraVe Cream", 4850.00, "50 ml") }
        findViewById<CardView>(R.id.btnAddGarnier).setOnClickListener { saveToDatabase("Garnier Serum", 2450.00, "30 ml") }
        findViewById<CardView>(R.id.btnAddLoreal).setOnClickListener { saveToDatabase("Loreal Cream", 3450.00, "50 g") }
        findViewById<CardView>(R.id.btnAddHimalaya).setOnClickListener { saveToDatabase("Himalaya Wash", 650.00, "100 ml") }
        findViewById<CardView>(R.id.btnAddNivea).setOnClickListener { saveToDatabase("Nivea Soft", 1150.00, "100 ml") }
        findViewById<CardView>(R.id.btnAddOlay).setOnClickListener { saveToDatabase("Olay Effects", 3850.00, "50 g") }
        findViewById<CardView>(R.id.btnAddVaseline).setOnClickListener { saveToDatabase("Vaseline Lotion", 1250.00, "200 ml") }


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


        val itemCetaphil = findViewById<LinearLayout>(R.id.itemCetaphil)
        val itemNeutrogena = findViewById<LinearLayout>(R.id.itemNeutrogena)
        val itemCerave = findViewById<LinearLayout>(R.id.itemCerave)
        val itemGarnier = findViewById<LinearLayout>(R.id.itemGarnier)
        val itemLoreal = findViewById<LinearLayout>(R.id.itemLoreal)
        val itemHimalaya = findViewById<LinearLayout>(R.id.itemHimalaya)
        val itemNivea = findViewById<LinearLayout>(R.id.itemNivea)
        val itemOlay = findViewById<LinearLayout>(R.id.itemOlay)
        val itemVaseline = findViewById<LinearLayout>(R.id.itemVaseline)

        val itemsList = listOf(itemCetaphil, itemNeutrogena, itemCerave, itemGarnier, itemLoreal, itemHimalaya, itemNivea, itemOlay, itemVaseline)
        val namesList = listOf("cetaphil cleanser", "neutrogena gel", "cerave cream", "garnier serum", "loreal cream", "himalaya wash", "nivea soft", "olay effects", "vaseline lotion")

        etSearchSkinCare.addTextChangedListener(object : TextWatcher {
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