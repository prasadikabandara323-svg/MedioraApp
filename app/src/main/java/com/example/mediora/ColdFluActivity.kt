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

class ColdFluActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cold_flu)


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
        val etSearchColdFlu = findViewById<EditText>(R.id.etSearchColdFlu)

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


        findViewById<CardView>(R.id.btnAddParacitamol).setOnClickListener { saveToDatabase("Paracitamol", 4.50, "500mg") }
        findViewById<CardView>(R.id.btnAddFludrex).setOnClickListener { saveToDatabase("Fludrex", 2950.00, "24 tablets") }
        findViewById<CardView>(R.id.btnAddPanadol).setOnClickListener { saveToDatabase("Panadol", 4.50, "500mg") }
        findViewById<CardView>(R.id.btnAddPiriton).setOnClickListener { saveToDatabase("Piriton", 5.50, "4mg") }
        findViewById<CardView>(R.id.btnAddCetirizine).setOnClickListener { saveToDatabase("Cetirizine", 7.50, "10mg") }
        findViewById<CardView>(R.id.btnAddLoratadine).setOnClickListener { saveToDatabase("Loratadine", 16.50, "10mg") }
        findViewById<CardView>(R.id.btnAddStrepsils).setOnClickListener { saveToDatabase("Strepsils", 25.00, "1 Lozenge") }
        findViewById<CardView>(R.id.btnAddVicks).setOnClickListener { saveToDatabase("Vicks 500", 200.00, "1 bottle") }
        findViewById<CardView>(R.id.btnAddSinarest).setOnClickListener { saveToDatabase("Sinarest", 18.00, "1 Tablet") }


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


        val itemParacitamol = findViewById<LinearLayout>(R.id.itemParacitamol)
        val itemFludrex = findViewById<LinearLayout>(R.id.itemFludrex)
        val itemPanadol = findViewById<LinearLayout>(R.id.itemPanadol)
        val itemPiriton = findViewById<LinearLayout>(R.id.itemPiriton)
        val itemCetirizine = findViewById<LinearLayout>(R.id.itemCetirizine)
        val itemLoratadine = findViewById<LinearLayout>(R.id.itemLoratadine)
        val itemStrepsils = findViewById<LinearLayout>(R.id.itemStrepsils)
        val itemVicks = findViewById<LinearLayout>(R.id.itemVicks)
        val itemSinarest = findViewById<LinearLayout>(R.id.itemSinarest)

        val itemsList = listOf(itemParacitamol, itemFludrex, itemPanadol, itemPiriton, itemCetirizine, itemLoratadine, itemStrepsils, itemVicks, itemSinarest)
        val namesList = listOf("paracitamol", "fludrex", "panadol", "piriton", "cetirizine", "loratadine", "strepsils", "vicks 500", "sinarest")

        etSearchColdFlu.addTextChangedListener(object : TextWatcher {
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