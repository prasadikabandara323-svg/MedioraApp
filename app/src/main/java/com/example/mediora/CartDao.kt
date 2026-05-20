package com.example.mediora

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Insert
    fun addToCart(cartItem: CartItem)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): List<CartItem>


    @Delete
    fun deleteCartItem(cartItem: CartItem)
}