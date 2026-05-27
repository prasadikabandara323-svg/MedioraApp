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

class AntibioticsActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_antibiotic2)


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
        val etSearchAntibiotics = findViewById<EditText>(R.id.etSearchAntibiotics)


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


        findViewById<CardView>(R.id.btnAddAmoxilin).setOnClickListener { saveToDatabase("Amoxilin", 18.50, "250mg") }
        findViewById<CardView>(R.id.btnAddAzithromycin).setOnClickListener { saveToDatabase("Azithromycin", 95.00, "250mg") }
        findViewById<CardView>(R.id.btnAddErythromycin).setOnClickListener { saveToDatabase("Erythromycin", 32.00, "250mg") }
        findViewById<CardView>(R.id.btnAddCefalexin).setOnClickListener { saveToDatabase("Cefalexin", 48.50, "500mg") }
        findViewById<CardView>(R.id.btnAddDoxycycline).setOnClickListener { saveToDatabase("Doxycycline", 28.00, "100mg") }
        findViewById<CardView>(R.id.btnAddCiprofloxacin).setOnClickListener { saveToDatabase("Ciprofloxacin", 42.00, "500mg") }
        findViewById<CardView>(R.id.btnAddLevofloxacin).setOnClickListener { saveToDatabase("Levofloxacin", 88.00, "500mg") }
        findViewById<CardView>(R.id.btnAddClindamycin).setOnClickListener { saveToDatabase("Clindamycin", 74.00, "300mg") }
        findViewById<CardView>(R.id.btnAddMetronidazole).setOnClickListener { saveToDatabase("Metronidazole", 14.00, "400mg") }


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


        val itemAmoxilin = findViewById<LinearLayout>(R.id.itemAmoxilin)
        val itemAzithromycin = findViewById<LinearLayout>(R.id.itemAzithromycin)
        val itemErythromycin = findViewById<LinearLayout>(R.id.itemErythromycin)
        val itemCefalexin = findViewById<LinearLayout>(R.id.itemCefalexin)
        val itemDoxycycline = findViewById<LinearLayout>(R.id.itemDoxycycline)
        val itemCiprofloxacin = findViewById<LinearLayout>(R.id.itemCiprofloxacin)
        val itemLevofloxacin = findViewById<LinearLayout>(R.id.itemLevofloxacin)
        val itemClindamycin = findViewById<LinearLayout>(R.id.itemClindamycin)
        val itemMetronidazole = findViewById<LinearLayout>(R.id.itemMetronidazole)

        val itemsList = listOf(itemAmoxilin, itemAzithromycin, itemErythromycin, itemCefalexin, itemDoxycycline, itemCiprofloxacin, itemLevofloxacin, itemClindamycin, itemMetronidazole)
        val namesList = listOf("amoxilin", "azithromycin", "erythromycin", "cefalexin", "doxycycline", "ciprofloxacin", "levofloxacin", "clindamycin", "metronidazole")

        etSearchAntibiotics.addTextChangedListener(object : TextWatcher {
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