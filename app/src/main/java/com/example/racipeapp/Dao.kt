package com.example.racipeapp

import androidx.room.Dao
import androidx.room.Query
@Dao
interface Dao {
    @Query("SELECT * FROM recipe")
    fun getAll():List<Recipe?>
//    @get:Query("SELECT * FROM recipe")
//    fun getAll():List<Recipe?>?
}