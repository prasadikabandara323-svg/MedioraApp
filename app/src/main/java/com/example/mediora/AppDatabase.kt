package com.example.mediora

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        User::class,
        Patient::class,
        CartItem::class,
        PharmacyOrder::class,
        DeliveryDetails::class,
        PrescriptionDetails::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {


    abstract fun userDao(): UserDao
    abstract fun patientDao(): PatientDao
    abstract fun cartDao(): CartDao
    abstract fun pharmacyDao(): PharmacyDao
    abstract fun deliveryDao(): DeliveryDao
    abstract fun prescriptionDao(): PrescriptionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mediora_database"
                )
                    .fallbackToDestructiveMigration() // Schema වෙනස්කම් සඳහා
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}