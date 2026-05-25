package com.example.mediora

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.graphics.toColorInt
import androidx.room.Room
import kotlin.concurrent.thread

class order_details : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "mediora_db_new"
        ).fallbackToDestructiveMigration()
            .build()

        val backArrow = findViewById<ImageView>(R.id.back_arrow)
        backArrow.setOnClickListener {
            finish()
        }

        val btnConfirmOrder = findViewById<CardView>(R.id.checkout_button)
        btnConfirmOrder.setOnClickListener {
            val intent = Intent(this, enterdelivery_details::class.java)
            startActivity(intent)
        }

        loadCartItems()
    }

    private fun loadCartItems() {
        thread {
            val cartList = database.cartDao().getAllCartItems()

            runOnUiThread {
                val container = findViewById<LinearLayout>(R.id.cart_items_container)
                val tvTotalAmount = findViewById<TextView>(R.id.tv_total_amount)

                container.removeAllViews()

                var totalCost = 0.0

                for (item in cartList) {
                    totalCost += (item.medicinePrice * item.quantity)

                    val cardView = CardView(this).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            setMargins(0, 0, 0, 30)
                        }
                        radius = 45f
                        cardElevation = 6f
                    }

                    val rowLayout = LinearLayout(this).apply {
                        orientation = LinearLayout.HORIZONTAL
                        setPadding(35, 35, 35, 35)
                        gravity = Gravity.CENTER_VERTICAL
                    }

                    val nameLower = item.medicineName.lowercase()

                    val imgMedicine = ImageView(this).apply {
                        layoutParams = LinearLayout.LayoutParams(180, 180)

                        when {
                            // Pain Relief Medicines
                            nameLower.contains("panadol") -> setImageResource(R.drawable.penad)
                            nameLower.contains("paracetamol") -> setImageResource(R.drawable.paracitamol)
                            nameLower.contains("ibuprofen") -> setImageResource(R.drawable.ibuprofen)
                            nameLower.contains("aspirin") -> setImageResource(R.drawable.aspirin)
                            nameLower.contains("diclofenac") -> setImageResource(R.drawable.diclofenac)
                            nameLower.contains("naproxen") -> setImageResource(R.drawable.napr)
                            nameLower.contains("tramadol") -> setImageResource(R.drawable.tramadol)
                            nameLower.contains("mefenamic") -> setImageResource(R.drawable.mefenamicacid)
                            nameLower.contains("celecoxib") -> setImageResource(R.drawable.celecoxib)

                            //  Antibiotics Medicines
                            nameLower.contains("amoxilin") -> setImageResource(R.drawable.amoxicillin)
                            nameLower.contains("azithromycin") -> setImageResource(R.drawable.azithormizin)
                            nameLower.contains("erythromycin") -> setImageResource(R.drawable.erithormyzin)
                            nameLower.contains("cefalexin") -> setImageResource(R.drawable.cefalexin)
                            nameLower.contains("doxycycline") -> setImageResource(R.drawable.doxycycline)
                            nameLower.contains("ciprofloxacin") -> setImageResource(R.drawable.ciprofloxacin)
                            nameLower.contains("levofloxacin") -> setImageResource(R.drawable.levofloxacin)
                            nameLower.contains("clindamycin") -> setImageResource(R.drawable.clindamycin)
                            nameLower.contains("metronidazole") -> setImageResource(R.drawable.metronidazole)

                            // Supplements Medicines
                            nameLower.contains("vitamin c") -> setImageResource(R.drawable.vitaminc)
                            nameLower.contains("omega-3") -> setImageResource(R.drawable.omega3)
                            nameLower.contains("fish oil") -> setImageResource(R.drawable.fishoill)
                            nameLower.contains("calcium") -> setImageResource(R.drawable.calcium)
                            nameLower.contains("vitamin e") -> setImageResource(R.drawable.vitamine)
                            nameLower.contains("zinc") -> setImageResource(R.drawable.zinc)
                            nameLower.contains("iron") -> setImageResource(R.drawable.iron)
                            nameLower.contains("multivitamin") -> setImageResource(R.drawable.multivitamin)
                            nameLower.contains("folic acid") -> setImageResource(R.drawable.folicacid)

                            //cold&flu
                            nameLower.contains("paracitamol") -> setImageResource(R.drawable.paracitamol)
                            nameLower.contains("fludrex") -> setImageResource(R.drawable.fludrex)
                            nameLower.contains("panadol") -> setImageResource(R.drawable.penad)
                            nameLower.contains("piriton") -> setImageResource(R.drawable.piriton)
                            nameLower.contains("cetirizine") -> setImageResource(R.drawable.cetirizine)
                            nameLower.contains("loratadine") -> setImageResource(R.drawable.loratadine)
                            nameLower.contains("strepsils") -> setImageResource(R.drawable.strepsils)
                            nameLower.contains("vicks 500") -> setImageResource(R.drawable.vicksaction)
                            nameLower.contains("sinarest") -> setImageResource(R.drawable.sinarest)

                            nameLower.contains("wellbaby liquid") -> setImageResource(R.drawable.vitabiotics)
                            nameLower.contains("vivamom") -> setImageResource(R.drawable.vivamom)
                            nameLower.contains("panadol syrup") -> setImageResource(R.drawable.panadolsyrup)
                            nameLower.contains("cheramy soap") -> setImageResource(R.drawable.babycheramysoap)
                            nameLower.contains("pears lotion") -> setImageResource(R.drawable.pearsbabylotion)
                            nameLower.contains("sudocrem") -> setImageResource(R.drawable.sudocrem)
                            nameLower.contains("lactogen 1") -> setImageResource(R.drawable.lactogen1)
                            nameLower.contains("gripe water") -> setImageResource(R.drawable.gripewater)
                            nameLower.contains("pregnacare") -> setImageResource(R.drawable.pregnacare)

                            //diabetes
                            nameLower.contains("metformin") -> setImageResource(R.drawable.metformin)
                            nameLower.contains("gliclazide") -> setImageResource(R.drawable.gliclazide)
                            nameLower.contains("glimepiride") -> setImageResource(R.drawable.glimepiride)
                            nameLower.contains("sitagliptin") -> setImageResource(R.drawable.sitagliptin)
                            nameLower.contains("vildagliptin") -> setImageResource(R.drawable.vildagliptin)
                            nameLower.contains("pioglitazone") -> setImageResource(R.drawable.pioglitazone)
                            nameLower.contains("empagliflozin") -> setImageResource(R.drawable.empagliflozin)
                            nameLower.contains("dapagliflozin") -> setImageResource(R.drawable.dapagliflozin)
                            nameLower.contains("linagliptin") -> setImageResource(R.drawable.linagliptin)

                            //blood pressure
                            nameLower.contains("amlodac") -> setImageResource(R.drawable.amlodac)
                            nameLower.contains("amlong") -> setImageResource(R.drawable.amlong)
                            nameLower.contains("carvedilol") -> setImageResource(R.drawable.carvedilol)
                            nameLower.contains("ramipril") -> setImageResource(R.drawable.ramipril)
                            nameLower.contains("losartan") -> setImageResource(R.drawable.losartan)
                            nameLower.contains("bisoprolol") -> setImageResource(R.drawable.bisoprolol)
                            nameLower.contains("indapamide") -> setImageResource(R.drawable.indapamide)
                            nameLower.contains("atenolol") -> setImageResource(R.drawable.atenolol)
                            nameLower.contains("valsartan") -> setImageResource(R.drawable.valsartan)

                            //skin care//
                            nameLower.contains("cetaphil cleanser") -> setImageResource(R.drawable.cetaphilcleanser)
                            nameLower.contains("neutrogena gel") -> setImageResource(R.drawable.neutrogenagel)
                            nameLower.contains("cerave cream") -> setImageResource(R.drawable.ceravecream)
                            nameLower.contains("garnier serum") -> setImageResource(R.drawable.garnierserum)
                            nameLower.contains("loreal cream") -> setImageResource(R.drawable.lorealcream)
                            nameLower.contains("himalaya wash") -> setImageResource(R.drawable.himalayawash)
                            nameLower.contains("nivea soft") -> setImageResource(R.drawable.niveasoft)
                            nameLower.contains("olay effects") -> setImageResource(R.drawable.olayeffects)
                            nameLower.contains("vaseline lotion") -> setImageResource(R.drawable.vaselinelotion)

                            //medical devices
                            nameLower.contains("bp monitor") -> setImageResource(R.drawable.digitalbloodpressuremonitor)
                            nameLower.contains("glucose meter") -> setImageResource(R.drawable.bloodglucosemeter)
                            nameLower.contains("pulse oximeter") -> setImageResource(R.drawable.pulseoximeter)
                            nameLower.contains("thermometer") -> setImageResource(R.drawable.digitalthermometer)
                            nameLower.contains("nebulizer") -> setImageResource(R.drawable.nebulizermachine)
                            nameLower.contains("ir thermometer") -> setImageResource(R.drawable.infraredthermometer)
                            nameLower.contains("weighing scale") -> setImageResource(R.drawable.digitalweighingscale)
                            nameLower.contains("stethoscope") -> setImageResource(R.drawable.stethoscope)
                            nameLower.contains("heating pad") -> setImageResource(R.drawable.heatingpad)
                            else -> setImageResource(R.drawable.amlodac)
                        }
                    }
                    val detailsLayout = LinearLayout(this).apply {
                        orientation = LinearLayout.VERTICAL
                        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                            setMargins(40, 0, 0, 0)
                        }
                    }

                    val tvName = TextView(this).apply {
                        text = "${item.medicineName} ${item.medicineMg}"
                        setTextColor("#1D2A64".toColorInt())
                        textSize = 18f
                        setTypeface(null, android.graphics.Typeface.BOLD)
                    }

                    val tvPrice = TextView(this).apply {
                        text = "Rs. ${item.medicinePrice}"
                        setTextColor("#4A49C6".toColorInt())
                        textSize = 16f
                    }

                    // QUANTITY CONTROLLER LAYOUT DESIGN (- , Qty , +)
                    val qtyControlLayout = LinearLayout(this).apply {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER_VERTICAL
                        setPadding(0, 15, 0, 0)
                    }

                    val btnMinus = CardView(this).apply {
                        layoutParams = LinearLayout.LayoutParams(65, 65)
                        radius = 20f
                        cardElevation = 2f
                        setCardBackgroundColor("#645CE6".toColorInt())
                    }
                    val tvMinusText = TextView(this).apply {
                        text = "-"
                        setTextColor(android.graphics.Color.WHITE)
                        textSize = 16f
                        gravity = Gravity.CENTER
                    }
                    btnMinus.addView(tvMinusText)

                    val tvQtyDisplay = TextView(this).apply {
                        text = "  ${item.quantity}  "
                        setTextColor("#1D2A64".toColorInt())
                        textSize = 16f
                        setTypeface(null, android.graphics.Typeface.BOLD)
                    }

                    val btnPlus = CardView(this).apply {
                        layoutParams = LinearLayout.LayoutParams(65, 65)
                        radius = 20f
                        cardElevation = 2f
                        setCardBackgroundColor("#645CE6".toColorInt())
                    }
                    val tvPlusText = TextView(this).apply {
                        text = "+"
                        setTextColor(android.graphics.Color.WHITE)
                        textSize = 16f
                        gravity = Gravity.CENTER
                    }
                    btnPlus.addView(tvPlusText)


                    btnMinus.setOnClickListener {
                        if (item.quantity > 1) {
                            thread {
                                database.cartDao().deleteCartItem(item)
                                val updatedItem = item.copy(quantity = item.quantity - 1)
                                database.cartDao().addToCart(updatedItem)
                                runOnUiThread { loadCartItems() }
                            }
                        } else {
                            Toast.makeText(this@order_details, "Quantity cannot be less than 1", Toast.LENGTH_SHORT).show()
                        }
                    }


                    btnPlus.setOnClickListener {
                        val isMedicalDevice = nameLower.contains("monitor") ||
                                nameLower.contains("meter") ||
                                nameLower.contains("oximeter") ||
                                nameLower.contains("thermometer") ||
                                nameLower.contains("nebulizer") ||
                                nameLower.contains("scale") ||
                                nameLower.contains("stethoscope") ||
                                nameLower.contains("pad")

                        if (isMedicalDevice) {
                            if (item.quantity < 10) { // Medical Devices ලිමිට් එක 10යි
                                thread {
                                    database.cartDao().deleteCartItem(item) // 🚀 පරණ අයිටම් එක මකනවා
                                    val updatedItem = item.copy(quantity = item.quantity + 1)
                                    database.cartDao().addToCart(updatedItem) // 🚀 අලුත් ගාණත් එක්ක සේව් කරනවා
                                    runOnUiThread { loadCartItems() }
                                }
                            } else {
                                Toast.makeText(this@order_details, "Maximum limit of 10 items reached for Medical Devices!", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            if (item.quantity < 30) { // Medicines ලිමිට් එක 30යි
                                thread {
                                    database.cartDao().deleteCartItem(item) // 🚀 පරණ අයිටම් එක මකනවා
                                    val updatedItem = item.copy(quantity = item.quantity + 1)
                                    database.cartDao().addToCart(updatedItem) // 🚀 අලුත් ගාණත් එක්ක සේව් කරනවා
                                    runOnUiThread { loadCartItems() }
                                }
                            } else {
                                Toast.makeText(this@order_details, "Maximum limit of 30 items reached for Medicines!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    qtyControlLayout.addView(btnMinus)
                    qtyControlLayout.addView(tvQtyDisplay)
                    qtyControlLayout.addView(btnPlus)

                    detailsLayout.addView(tvName)
                    detailsLayout.addView(tvPrice)
                    detailsLayout.addView(qtyControlLayout)

                    val imgDelete = ImageView(this).apply {
                        layoutParams = LinearLayout.LayoutParams(80, 80).apply {
                            gravity = Gravity.TOP
                        }
                        setImageResource(android.R.drawable.ic_menu_delete)
                        setColorFilter(android.graphics.Color.parseColor("#F44336"))
                        isClickable = true
                        isFocusable = true
                    }

                    imgDelete.setOnClickListener {
                        thread {
                            database.cartDao().deleteCartItem(item)

                            runOnUiThread {
                                Toast.makeText(this@order_details, "${item.medicineName} removed from cart", Toast.LENGTH_SHORT).show()
                                loadCartItems()
                            }
                        }
                    }

                    rowLayout.addView(imgMedicine)
                    rowLayout.addView(detailsLayout)
                    rowLayout.addView(imgDelete)

                    cardView.addView(rowLayout)
                    container.addView(cardView)
                }

                tvTotalAmount.text = "Rs. ${String.format("%.2f", totalCost)}"
            }
        }
    }
}