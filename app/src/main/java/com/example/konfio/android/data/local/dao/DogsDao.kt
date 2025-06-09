package com.example.konfio.android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.konfio.android.data.local.entity.DogEntity

@Dao
interface DogsDao {
    @Query("SELECT * FROM dogs")
    suspend fun getDogs(): List<DogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogs(dogs: List<DogEntity>)

    @Query("SELECT COUNT(*) FROM dogs")
    suspend fun getDogsCount(): Int
} 