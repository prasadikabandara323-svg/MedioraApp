package com.example.mediora

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val medicineName: String,
    val medicinePrice: Double,
    val medicineMg: String,
    val quantity: Int,
    val imageResource: String
)