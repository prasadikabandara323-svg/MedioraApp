package com.example.mediora

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// මෙතන entities ලැයිස්තුවට User එකත් එකතු කරන්න
@Database(entities = [User::class ,Patient::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun patientDao(): PatientDao
    // අලුතින් හදපු UserDao එක මෙතනට එකතු කරන්න
    abstract fun userDao(): UserDao

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
                    .fallbackToDestructiveMigration() // මෙය එකතු කරන්න (Schema වෙනස් වන නිසා)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}