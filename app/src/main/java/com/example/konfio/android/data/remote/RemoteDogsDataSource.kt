package com.example.konfio.android.data.remote

import com.example.konfio.android.data.datasource.readable.DogsReadableDataSource
import com.example.konfio.android.data.mapper.toDog
import com.example.konfio.android.domain.model.Dog
import javax.inject.Inject

class RemoteDogsDataSource @Inject constructor(
    private val api: DogsApi
): DogsReadableDataSource {
    override suspend fun getDogs(): List<Dog> {
        return api.getDogs().map { it.toDog() }
    }
} 