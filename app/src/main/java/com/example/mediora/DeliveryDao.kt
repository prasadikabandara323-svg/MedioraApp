package com.example.mediora

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface DeliveryDao {
    @Insert
    fun insertDelivery(delivery: DeliveryDetails)
}