package com.example.mediora

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface PrescriptionDao {
    @Insert
    fun insertPrescription(prescription: PrescriptionDetails)
}