package com.example.mediora

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Patient")
data class Patient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientName: String,
    val ageGender: String,
    val contactNumber: String,
    val reasonForVisit: String,
    val previousRecords: String
)