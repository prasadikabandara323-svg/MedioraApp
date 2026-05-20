package com.example.mediora

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prescription_table")
data class PrescriptionDetails(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientName: String,
    val age: String,
    val days: String,
    val imageUri: String,
    val status: String = "Pending"
)