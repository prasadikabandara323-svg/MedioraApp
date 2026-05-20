package com.example.mediora

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PharmacyDao {


    @Insert
    suspend fun insertOrder(order: PharmacyOrder)


    @Query("SELECT * FROM pharmacy_orders ORDER BY id DESC")
    suspend fun getAllOrders(): List<PharmacyOrder>
}