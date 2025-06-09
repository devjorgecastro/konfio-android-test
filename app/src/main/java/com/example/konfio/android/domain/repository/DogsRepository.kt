package com.example.konfio.android.domain.repository

import com.example.konfio.android.domain.model.Dog

interface DogsRepository {
    suspend fun getDogs(): List<Dog>
} 