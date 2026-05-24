package com.example.mediora

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog

class ReviewActivity : AppCompatActivity() {

    private val previousReviewsList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        // 1. UI Components
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val txtRatingValue = findViewById<TextView>(R.id.txtRatingValue)
        val edtFeedback = findViewById<EditText>(R.id.edtFeedback)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val btnDisplayReviews = findViewById<Button>(R.id.btnDisplayReviews)
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnSendFeedback = findViewById<ImageView>(R.id.btnSendFeedback)

        // 2. Bottom Navigation Bar Components
        val navHome = findViewById<LinearLayout>(R.id.navHomeLayout)
        val navPharmacy = findViewById<LinearLayout>(R.id.navPharmacyLayout)
        val navChanneling = findViewById<LinearLayout>(R.id.navChannelingLayout)
        val navAccount = findViewById<LinearLayout>(R.id.navSearchLayout)

        // 3. Navigation Click Listeners
        navHome.setOnClickListener {
            Toast.makeText(this, "Navigating to Home", Toast.LENGTH_SHORT).show()
            // start activity here if needed
        }

        navPharmacy.setOnClickListener {
            Toast.makeText(this, "Navigating to Pharmacy", Toast.LENGTH_SHORT).show()
        }

        navChanneling.setOnClickListener {
            Toast.makeText(this, "Navigating to E-Channeling", Toast.LENGTH_SHORT).show()
        }

        navAccount.setOnClickListener {
            Toast.makeText(this, "Navigating to Account", Toast.LENGTH_SHORT).show()
        }

        // 4. Existing Logic
        ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, rating, _ ->
            txtRatingValue.text = rating.toString()
        }

        val submitAction = {
            val userRating = ratingBar.rating.toString()
            val userFeedback = edtFeedback.text.toString().trim()

            if (userFeedback.isNotEmpty()) {
                val fullReview = "⭐ $userRating/5.0 - $userFeedback"
                previousReviewsList.add(fullReview)
                Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show()
                edtFeedback.text.clear()
                ratingBar.rating = 5.0f
            } else {
                Toast.makeText(this, "Please share your feedback first!", Toast.LENGTH_SHORT).show()
            }
        }

        btnSubmit.setOnClickListener { submitAction() }
        btnSendFeedback.setOnClickListener { submitAction() }

        btnDisplayReviews.setOnClickListener {
            if (previousReviewsList.isNotEmpty()) {
                val allReviewsText = StringBuilder()
                for (review in previousReviewsList) {
                    allReviewsText.append(review).append("\n\n")
                }
                AlertDialog.Builder(this).apply {
                    setTitle("Previous Reviews")
                    setMessage(allReviewsText.toString().trim())
                    setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    show()
                }
            } else {
                Toast.makeText(this, "No previous reviews found!", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener { finish() }
    }
}