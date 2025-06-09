package com.example.konfio.android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogs")
data class DogEntity(
    @PrimaryKey
    val dogName: String,
    val description: String,
    val age: Int,
    val image: String
) 