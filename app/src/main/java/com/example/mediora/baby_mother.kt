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

class baby_mother : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_baby_mother2)


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
        val etSearchBabyMother = findViewById<EditText>(R.id.etSearchBabyMother)

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


        findViewById<CardView>(R.id.btnAddWellbaby).setOnClickListener { saveToDatabase("Wellbaby Liquid", 5775.00, "150 ml") }
        findViewById<CardView>(R.id.btnAddVivamom).setOnClickListener { saveToDatabase("Vivamom", 3780.00, "400 g") }
        findViewById<CardView>(R.id.btnAddPanadolSyrup).setOnClickListener { saveToDatabase("Panadol Syrup", 370.00, "100 ml") }
        findViewById<CardView>(R.id.btnAddCheramySoap).setOnClickListener { saveToDatabase("Cheramy Soap", 180.00, "100 g") }
        findViewById<CardView>(R.id.btnAddPearsLotion).setOnClickListener { saveToDatabase("Pears Lotion", 350.00, "100 ml") }
        findViewById<CardView>(R.id.btnAddSudocrem).setOnClickListener { saveToDatabase("Sudocrem", 2500.00, "400 g") }
        findViewById<CardView>(R.id.btnAddLactogen).setOnClickListener { saveToDatabase("Lactogen 1", 2800.00, "400 g") }
        findViewById<CardView>(R.id.btnAddGripeWater).setOnClickListener { saveToDatabase("Gripe Water", 850.00, "130 ml") }
        findViewById<CardView>(R.id.btnAddPregnacare).setOnClickListener { saveToDatabase("Pregnacare", 3200.00, "30 Tablets") }


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


        val itemWellbaby = findViewById<LinearLayout>(R.id.itemWellbaby)
        val itemVivamom = findViewById<LinearLayout>(R.id.itemVivamom)
        val itemPanadolSyrup = findViewById<LinearLayout>(R.id.itemPanadolSyrup)
        val itemCheramySoap = findViewById<LinearLayout>(R.id.itemCheramySoap)
        val itemPearsLotion = findViewById<LinearLayout>(R.id.itemPearsLotion)
        val itemSudocrem = findViewById<LinearLayout>(R.id.itemSudocrem)
        val itemLactogen = findViewById<LinearLayout>(R.id.itemLactogen)
        val itemGripeWater = findViewById<LinearLayout>(R.id.itemGripeWater)
        val itemPregnacare = findViewById<LinearLayout>(R.id.itemPregnacare)

        val itemsList = listOf(itemWellbaby, itemVivamom, itemPanadolSyrup, itemCheramySoap, itemPearsLotion, itemSudocrem, itemLactogen, itemGripeWater, itemPregnacare)
        val namesList = listOf("wellbaby liquid", "vivamom", "panadol syrup", "cheramy soap", "pears lotion", "sudocrem", "lactogen 1", "gripe water", "pregnacare")

        etSearchBabyMother.addTextChangedListener(object : TextWatcher {
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