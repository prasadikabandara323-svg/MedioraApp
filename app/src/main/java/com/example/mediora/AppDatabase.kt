package com.example.mediora

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Patient::class, PharmacyOrder::class, CartItem::class, DeliveryDetails::class, PrescriptionDetails::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun patientDao(): PatientDao

    abstract fun pharmacyDao(): PharmacyDao

    abstract fun cartDao(): CartDao

    abstract fun deliveryDao(): DeliveryDao

    abstract fun prescriptionDao(): PrescriptionDao
}