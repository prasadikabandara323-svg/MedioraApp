package com.example.mediora

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PatientDao {
    @Insert
    suspend fun insertPatient(patient: Patient)

    @Query("SELECT * FROM Patient WHERE patientName = :name LIMIT 1")
    suspend fun getPatientByName(name: String): Patient?
}