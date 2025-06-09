package com.example.konfio.android.data.repository

import com.example.konfio.android.data.mapper.toDog
import com.example.konfio.android.data.remote.DogsApi
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.domain.repository.DogsRepository
import javax.inject.Inject

class DefaultDogsRepository @Inject constructor(
    private val api: DogsApi
) : DogsRepository {
    override suspend fun getDogs(): List<Dog> {
        return api.getDogs().map { it.toDog() }
    }
} 