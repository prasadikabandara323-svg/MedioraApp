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

class PainReliefActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pain_relief)


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
        val etSearchPainRelief = findViewById<EditText>(R.id.etSearchPainRelief)

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


        findViewById<CardView>(R.id.btnAddParacetamol).setOnClickListener { saveToDatabase("Paracitamol", 4.50, "500mg") }
        findViewById<CardView>(R.id.btnAddIbuprofen).setOnClickListener { saveToDatabase("Ibuprofen", 8.50, "200mg") }
        findViewById<CardView>(R.id.btnAddPanadol).setOnClickListener { saveToDatabase("Panadol", 4.50, "500mg") }
        findViewById<CardView>(R.id.btnAddAspirin).setOnClickListener { saveToDatabase("Aspirin", 12.00, "300mg") }
        findViewById<CardView>(R.id.btnAddDiclofenac).setOnClickListener { saveToDatabase("Diclofenac", 18.50, "50mg") }
        findViewById<CardView>(R.id.btnAddNaproxen).setOnClickListener { saveToDatabase("Naproxen", 24.00, "250mg") }


        findViewById<LinearLayout>(R.id.itemTramadol).setOnClickListener { saveToDatabase("Tramadol", 35.00, "50mg") }
        findViewById<LinearLayout>(R.id.itemMefenamic).setOnClickListener { saveToDatabase("Mefenamic", 22.00, "500mg") }
        findViewById<LinearLayout>(R.id.itemCelecoxib).setOnClickListener { saveToDatabase("Celecoxib", 45.00, "200mg") }


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


        val itemParacetamol = findViewById<LinearLayout>(R.id.itemParacetamol)
        val itemIbuprofen = findViewById<LinearLayout>(R.id.itemIbuprofen)
        val itemPanadol = findViewById<LinearLayout>(R.id.itemPanadol)
        val itemAspirin = findViewById<LinearLayout>(R.id.itemAspirin)
        val itemDiclofenac = findViewById<LinearLayout>(R.id.itemDiclofenac)
        val itemNaproxen = findViewById<LinearLayout>(R.id.itemNaproxen)
        val itemTramadol = findViewById<LinearLayout>(R.id.itemTramadol)
        val itemMefenamic = findViewById<LinearLayout>(R.id.itemMefenamic)
        val itemCelecoxib = findViewById<LinearLayout>(R.id.itemCelecoxib)

        val itemsList = listOf(itemParacetamol, itemIbuprofen, itemPanadol, itemAspirin, itemDiclofenac, itemNaproxen, itemTramadol, itemMefenamic, itemCelecoxib)
        val namesList = listOf("paracitamol", "ibuprofen", "panadol", "aspirin", "diclofenac", "naproxen", "tramadol", "mefenamic", "celecoxib")

        etSearchPainRelief.addTextChangedListener(object : TextWatcher {
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