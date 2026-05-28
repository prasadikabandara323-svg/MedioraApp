package com.example.mediora

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.room.Room
import kotlin.concurrent.thread

class enterdelivery_details : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private var medicineTotalAmount: Double = 0.0
    private var deliveryFeeAmount: Double = 300.0 // Standard Delivery fee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enterdelivery_details)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "mediora_db_new"
        ).fallbackToDestructiveMigration()
            .build()

        val backArrow = findViewById<ImageView>(R.id.back_arrow)
        backArrow.setOnClickListener { finish() }

        val tvMedicineTotal = findViewById<TextView>(R.id.tv_medicine_total)
        val tvDeliveryFee = findViewById<TextView>(R.id.tv_delivery_fee)
        val tvFinalTotal = findViewById<TextView>(R.id.tv_final_total)

        val etName = findViewById<EditText>(R.id.et_customer_name)
        val etAddress = findViewById<EditText>(R.id.et_customer_address)
        val etPhone = findViewById<EditText>(R.id.et_customer_phone)
        val rgDeliveryMethod = findViewById<RadioGroup>(R.id.rg_delivery_method)


        val bottomNav = findViewById<LinearLayout>(R.id.bottom_nav)
        val navHome = bottomNav.getChildAt(0) as LinearLayout
        val navPharmacy = bottomNav.getChildAt(1) as LinearLayout
        val navEChanneling = bottomNav.getChildAt(2) as LinearLayout
        val navAccount = bottomNav.getChildAt(3) as LinearLayout


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


        thread {
            val cartItems = database.cartDao().getAllCartItems()
            medicineTotalAmount = cartItems.sumOf { it.medicinePrice * it.quantity }

            runOnUiThread {
                tvMedicineTotal.text = "Rs. ${String.format("%.2f", medicineTotalAmount)}"
                updateTotalAmount(tvDeliveryFee, tvFinalTotal)
            }
        }

        rgDeliveryMethod.setOnCheckedChangeListener { _, checkedId ->
            deliveryFeeAmount = if (checkedId == R.id.rb_express) {
                500.0 // Express Delivery fee
            } else {
                300.0 // Standard Delivery fee
            }
            updateTotalAmount(tvDeliveryFee, tvFinalTotal)
        }


        val btnSaveConfirm = findViewById<CardView>(R.id.btn_save_confirm)
        btnSaveConfirm.setOnClickListener {
            val name = etName.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val phone = etPhone.text.toString().trim()

            val selectedMethod = if (rgDeliveryMethod.checkedRadioButtonId == R.id.rb_express) "Express Delivery" else "Standard Delivery"
            val finalTotal = medicineTotalAmount + deliveryFeeAmount

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill in all details!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            thread {
                val newDelivery = DeliveryDetails(
                    customerName = name,
                    customerAddress = address,
                    phoneNumber = phone,
                    deliveryMethod = selectedMethod,
                    medicineTotal = medicineTotalAmount,
                    deliveryFee = deliveryFeeAmount,
                    finalTotal = finalTotal
                )
                database.deliveryDao().insertDelivery(newDelivery)

                runOnUiThread {
                    Toast.makeText(this@enterdelivery_details, "Order Confirmed Successfully!", Toast.LENGTH_LONG).show()


                    val intent = Intent(this@enterdelivery_details, CardDetailsActivity::class.java)
                    startActivity(intent)

                    finish()
                }
            }
        }
    }

    private fun updateTotalAmount(tvFee: TextView, tvTotal: TextView) {
        tvFee.text = "Rs. ${String.format("%.2f", deliveryFeeAmount)}"
        val finalAmount = medicineTotalAmount + deliveryFeeAmount
        tvTotal.text = "Rs. ${String.format("%.2f", finalAmount)}"
    }
}