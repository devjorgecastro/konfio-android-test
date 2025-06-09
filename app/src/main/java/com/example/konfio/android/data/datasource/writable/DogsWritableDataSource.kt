package com.example.konfio.android.data.datasource.writable

import com.example.konfio.android.domain.model.Dog

interface DogsWritableDataSource {
    suspend fun saveDogs(dogs: List<Dog>)
}