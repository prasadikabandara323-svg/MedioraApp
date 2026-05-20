package com.example.mediora

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "delivery_table")
data class DeliveryDetails(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val customerName: String,
    val customerAddress: String,
    val phoneNumber: String,
    val deliveryMethod: String,
    val medicineTotal: Double,
    val deliveryFee: Double,
    val finalTotal: Double,



)