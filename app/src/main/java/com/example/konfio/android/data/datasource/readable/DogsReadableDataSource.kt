package com.example.konfio.android.data.datasource.readable

import com.example.konfio.android.domain.model.Dog

interface DogsReadableDataSource {
    suspend fun getDogs(): List<Dog>
}