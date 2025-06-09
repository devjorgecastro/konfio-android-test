package com.example.konfio.android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.konfio.android.data.local.dao.DogsDao
import com.example.konfio.android.data.local.entity.DogEntity

@Database(
    entities = [DogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DogsDatabase : RoomDatabase() {
    abstract val dogsDao: DogsDao
} 