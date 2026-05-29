package com.example.mediora

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

data class Medicine(var name: String, var dose: String, var time: String, var isTaken: Boolean = false)

class Mymed : AppCompatActivity() {

    private val quickDoctorMedsSuggestions = arrayOf("Panadol", "Amoxicillin", "Lisinopril", "Insulin", "Metformin", "Atorvastatin")
    private val allMedsList = ArrayList<Medicine>()
    private val displayMedsList = ArrayList<Medicine>()

    private var selectedPosition: Int = -1
    private lateinit var containerLayout: LinearLayout
    private lateinit var edtSearchMeds: EditText
    private lateinit var txtTakenStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mymed)

        // UI Components
        containerLayout = findViewById(R.id.layoutMedsContainer)
        edtSearchMeds = findViewById(R.id.edtSearchMeds)
        val btnAddMed = findViewById<ImageView>(R.id.btnAddMed)
        val btnDeleteMed = findViewById<Button>(R.id.btnDeleteMed)
        val btnEditMed = findViewById<Button>(R.id.btnEditMed)
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val txtCurrentDay = findViewById<TextView>(R.id.txtCurrentDay)
        val txtCurrentTime = findViewById<TextView>(R.id.txtCurrentTime)
        txtTakenStatus = findViewById(R.id.txtTakenStatus)

        // Date & Time
        val sdfDay = SimpleDateFormat("EEE", Locale.getDefault())
        val sdfTime = SimpleDateFormat("hh:mm a", Locale.getDefault())
        txtCurrentDay.text = sdfDay.format(Date()).uppercase()
        txtCurrentTime.text = sdfTime.format(Date())

        // Initial Data
        allMedsList.add(Medicine("Lisinopril", "10 mg", "08.00 AM", true))
        allMedsList.add(Medicine("Insulin", "10 units", "12.00 PM", false))
        allMedsList.add(Medicine("Amoxicillin", "500mg", "08.00 PM", false))
        displayMedsList.addAll(allMedsList)
        updateMedsUI()

        setupBottomNavigation()

        btnAddMed.setOnClickListener { showMedDialog(false) }
        btnEditMed.setOnClickListener {
            if (selectedPosition != -1) showMedDialog(true)
            else Toast.makeText(this, "Select a medicine first!", Toast.LENGTH_SHORT).show()
        }
        btnDeleteMed.setOnClickListener {
            if (selectedPosition != -1) {
                allMedsList.remove(displayMedsList[selectedPosition])
                displayMedsList.removeAt(selectedPosition)
                selectedPosition = -1
                updateMedsUI()
            } else Toast.makeText(this, "Select a medicine to delete!", Toast.LENGTH_SHORT).show()
        }
        btnBack.setOnClickListener { finish() }
    }

    private fun setupBottomNavigation() {
        val layoutBottomNav = findViewById<LinearLayout>(R.id.layoutBottomNav)
        val btnHome = layoutBottomNav.getChildAt(0)
        val btnPharmacy = layoutBottomNav.getChildAt(1)
        val btnEChanneling = layoutBottomNav.getChildAt(2)
        val btnAccount = layoutBottomNav.getChildAt(3)

        btnHome.setOnClickListener { navigateTo(home::class.java) }
        btnPharmacy.setOnClickListener { navigateTo(PharmacyActivity::class.java) }
        btnEChanneling.setOnClickListener { navigateTo(EBookingActivity::class.java) }
        btnAccount.setOnClickListener { navigateTo(ProfileActivity::class.java) }
    }

    private fun navigateTo(activityClass: Class<*>) {
        if (this::class.java != activityClass) {
            val intent = Intent(this, activityClass)
            startActivity(intent)
            finish()
        }
    }

    private fun updateMedsUI() {
        containerLayout.removeAllViews()
        var takenCount = 0
        for (i in displayMedsList.indices) {
            val med = displayMedsList[i]
            if (med.isTaken) takenCount++

            val rowView = RelativeLayout(this).apply {
                layoutParams = LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, 0, 0, 16) }
                setPadding(40, 45, 40, 45)
                setBackgroundColor(if (i == selectedPosition) Color.parseColor("#0E3982") else Color.parseColor("#2BBED4"))
            }

            val txtNameAndDose = TextView(this).apply {
                text = "${med.name}\n${med.dose}"
                setTextColor(Color.WHITE)
                textSize = 16f
                setTypeface(null, Typeface.BOLD)
            }

            rowView.addView(txtNameAndDose)
            rowView.setOnClickListener { selectedPosition = if (selectedPosition == i) -1 else i; updateMedsUI() }
            containerLayout.addView(rowView)
        }
        txtTakenStatus.text = "$takenCount/${displayMedsList.size} taken today"
    }

    private fun showMedDialog(isEdit: Boolean) {
        val builder = AlertDialog.Builder(this)
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(50, 40, 50, 40) }
        val edtName = EditText(this).apply { hint = "Name" }
        val edtDose = EditText(this).apply { hint = "Dose" }
        val edtTime = EditText(this).apply { hint = "Time" }

        if (isEdit) {
            edtName.setText(displayMedsList[selectedPosition].name)
            edtDose.setText(displayMedsList[selectedPosition].dose)
            edtTime.setText(displayMedsList[selectedPosition].time)
        }

        layout.addView(edtName); layout.addView(edtDose); layout.addView(edtTime)
        builder.setView(layout)
        builder.setPositiveButton("Save") { _, _ ->
            if (isEdit) {
                val med = displayMedsList[selectedPosition]
                med.name = edtName.text.toString()
                med.dose = edtDose.text.toString()
                med.time = edtTime.text.toString()
            } else {
                allMedsList.add(Medicine(edtName.text.toString(), edtDose.text.toString(), edtTime.text.toString()))
                displayMedsList.clear()
                displayMedsList.addAll(allMedsList)
            }
            updateMedsUI()
        }
        builder.show()
    }
}