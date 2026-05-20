package com.example.mediora

data class Review(
    val reviewerName: String,
    val reviewText: String,
    val ratingStars: Int = 5
)

data class Doctor(
    val id: String,
    val name: String,
    val specialty: String,
    val imageRes: Int,
    val date: String,
    val time: String,
    val location: String,
    val totalFee: String,
    val notice: String,
    val patientsCount: String,
    val experienceYrs: String,
    val ratingValue: String,
    val reviews: List<Review>
)