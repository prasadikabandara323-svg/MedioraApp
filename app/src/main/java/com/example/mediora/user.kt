package com.example.mediora

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val email: String,
    val password: String,
    val phone: String,
    val age: String,
    val gender: String,
    val height: String,
    val weight: String,
    val bloodGroup: String,

    val profilePhotoUri: String? = null
)