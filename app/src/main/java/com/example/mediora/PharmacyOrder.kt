package com.example.mediora

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pharmacy_orders")
data class PharmacyOrder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val customerName: String,
    val customerAddress: String,
    val phoneNumber: String,
    val deliveryMethod: String,
    val medicineTotal: Double,
    val deliveryFee: Double,
    val totalAmount: Double,
    val prescriptionDays: Int = 0
)