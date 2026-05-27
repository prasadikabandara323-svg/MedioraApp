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

class SupplementsActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_suppliment)


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
        val etSearchSupplements = findViewById<EditText>(R.id.etSearchSupplements)


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


        findViewById<CardView>(R.id.btnAddVitaminC).setOnClickListener { saveToDatabase("Vitamin C", 15.00, "500mg") }
        findViewById<CardView>(R.id.btnAddOmega3).setOnClickListener { saveToDatabase("Omega-3", 55.00, "1000mg") }
        findViewById<CardView>(R.id.btnAddFishOil).setOnClickListener { saveToDatabase("Fish oil", 55.00, "1g") }
        findViewById<CardView>(R.id.btnAddCalcium).setOnClickListener { saveToDatabase("Calcium", 22.00, "500mg") }
        findViewById<CardView>(R.id.btnAddVitaminE).setOnClickListener { saveToDatabase("Vitamin E", 45.00, "400mg") }
        findViewById<CardView>(R.id.btnAddZinc).setOnClickListener { saveToDatabase("Zinc", 18.00, "50mg") }
        findViewById<CardView>(R.id.btnAddIron).setOnClickListener { saveToDatabase("Iron", 20.00, "200mg") }
        findViewById<CardView>(R.id.btnAddMultivitamin).setOnClickListener { saveToDatabase("Multivitamin", 65.00, "1 Tablet") }
        findViewById<CardView>(R.id.btnAddFolicAcid).setOnClickListener { saveToDatabase("Folic Acid", 12.50, "1mg") }


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


        val itemVitaminC = findViewById<LinearLayout>(R.id.itemVitaminC)
        val itemOmega3 = findViewById<LinearLayout>(R.id.itemOmega3)
        val itemFishOil = findViewById<LinearLayout>(R.id.itemFishOil)
        val itemCalcium = findViewById<LinearLayout>(R.id.itemCalcium)
        val itemVitaminE = findViewById<LinearLayout>(R.id.itemVitaminE)
        val itemZinc = findViewById<LinearLayout>(R.id.itemZinc)
        val itemIron = findViewById<LinearLayout>(R.id.itemIron)
        val itemMultivitamin = findViewById<LinearLayout>(R.id.itemMultivitamin)
        val itemFolicAcid = findViewById<LinearLayout>(R.id.itemFolicAcid)

        val itemsList = listOf(itemVitaminC, itemOmega3, itemFishOil, itemCalcium, itemVitaminE, itemZinc, itemIron, itemMultivitamin, itemFolicAcid)
        val namesList = listOf("vitamin c", "omega-3", "fish oil", "calcium", "vitamin e", "zinc", "iron", "multivitamin", "folic acid")

        etSearchSupplements.addTextChangedListener(object : TextWatcher {
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