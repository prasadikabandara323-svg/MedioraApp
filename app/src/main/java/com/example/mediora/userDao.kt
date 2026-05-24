package com.example.mediora

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update // මෙය අනිවාර්යයෙන් import කරන්න

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    // මීට අමතරව Update ශ්‍රිතය මෙතනට එකතු කරන්න
    @Update
    suspend fun updateUser(user: User)

    // මෙහි 'suspend' යන්න එක් කරන්න
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}