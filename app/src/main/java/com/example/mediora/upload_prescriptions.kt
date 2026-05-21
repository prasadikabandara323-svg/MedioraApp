package com.example.mediora

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.room.Room
import com.example.mediora.PrescriptionDetails
import kotlin.concurrent.thread

class upload_prescriptions : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private var selectedImageUri: String = ""
    private lateinit var tvFileName: TextView
    private lateinit var cvFilePreview: CardView

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri.toString()
            tvFileName.text = "Prescription_from_gallery.jpg"
            cvFilePreview.visibility = View.VISIBLE
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show()
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            selectedImageUri = "Camera_Capture_${System.currentTimeMillis()}.jpg"
            tvFileName.text = "Captured_Prescription.jpg"
            cvFilePreview.visibility = View.VISIBLE
            Toast.makeText(this, "Photo captured", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_prescriptions)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "mediora_db_new"
        ).fallbackToDestructiveMigration()
            .build()

        val backArrow = findViewById<ImageView>(R.id.back_arrow)
        backArrow.setOnClickListener { finish() }

        val etPatientName = findViewById<EditText>(R.id.et_patient_name)
        val etAge = findViewById<EditText>(R.id.et_age)
        val etDays = findViewById<EditText>(R.id.et_days)
        val btnSendPharmacy = findViewById<CardView>(R.id.btn_send_pharmacy)

        tvFileName = findViewById(R.id.tv_file_name)
        cvFilePreview = findViewById(R.id.cv_file_preview)
        val btnClearImage = findViewById<ImageView>(R.id.btn_clear_image)

        val btnGallery = findViewById<CardView>(R.id.btn_gallery)
        val btnTakePhoto = findViewById<CardView>(R.id.btn_take_photo)

        val bottomNav = findViewById<LinearLayout>(R.id.bottom_nav)
        val navHome = bottomNav.getChildAt(0) as LinearLayout
        val navPharmacy = bottomNav.getChildAt(1) as LinearLayout
        val navEChanneling = bottomNav.getChildAt(2) as LinearLayout
        val navAccount = bottomNav.getChildAt(3) as LinearLayout

        navHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        navPharmacy.setOnClickListener {
            val intent = Intent(this, PharmacyActivity::class.java)
            startActivity(intent)
            finish()
        }

        navEChanneling.setOnClickListener {
            Toast.makeText(this, "E-Channeling page is not connected yet", Toast.LENGTH_SHORT).show()
        }

        navAccount.setOnClickListener {
            Toast.makeText(this, "Account profile coming soon!", Toast.LENGTH_SHORT).show()
        }

        btnGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        btnTakePhoto.setOnClickListener {
            cameraLauncher.launch(null)
        }

        btnClearImage.setOnClickListener {
            selectedImageUri = ""
            cvFilePreview.visibility = View.GONE
            Toast.makeText(this, "Image removed", Toast.LENGTH_SHORT).show()
        }

        cvFilePreview.setOnClickListener {
            if (selectedImageUri.isNotEmpty() && selectedImageUri.startsWith("content://")) {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(Uri.parse(selectedImageUri), "image/*")
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "Cannot open this image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preview not available for camera captures yet.", Toast.LENGTH_SHORT).show()
            }
        }

        btnSendPharmacy.setOnClickListener {
            val name = etPatientName.text.toString().trim()
            val age = etAge.text.toString().trim()
            val days = etDays.text.toString().trim()

            if (selectedImageUri.isEmpty()) {
                Toast.makeText(this, "Please upload a prescription image!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (name.isEmpty() || age.isEmpty() || days.isEmpty()) {
                Toast.makeText(this, "Please fill in all details!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            thread {
                val newPrescription = PrescriptionDetails(
                    patientName = name,
                    age = age,
                    days = days,
                    imageUri = selectedImageUri,
                    status = "Pending"
                )
                database.prescriptionDao().insertPrescription(newPrescription)

                runOnUiThread {
                    val builder = AlertDialog.Builder(this@upload_prescriptions)
                    builder.setTitle("Prescription Sent Successfully! ✅")
                    builder.setMessage("We have received your prescription. Our pharmacist will review it and notify you of the total price shortly.")
                    builder.setCancelable(false)


                    builder.setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                        val intent = Intent(this@upload_prescriptions, PharmacyActivity::class.java)

                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }

                    val alertDialog = builder.create()
                    alertDialog.show()
                }
            }
        }
    }
}