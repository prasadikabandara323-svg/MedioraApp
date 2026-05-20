package com.example.mediora
import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class AppDatabase : RoomDatabase() {

    abstract fun patientDao(): PatientDao
    abstract fun pharmacyDao(): PharmacyDao

    abstract fun cartDao(): CartDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mediora_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
    abstract fun deliveryDao(): DeliveryDao

    abstract fun prescriptionDao(): PrescriptionDao
}