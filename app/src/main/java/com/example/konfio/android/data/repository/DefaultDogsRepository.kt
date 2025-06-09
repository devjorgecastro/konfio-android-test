package com.example.konfio.android.data.repository

import com.example.konfio.android.data.datasource.readable.DogsReadableDataSource
import com.example.konfio.android.di.RemoteDataSource
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.domain.repository.DogsRepository
import javax.inject.Inject

class DefaultDogsRepository @Inject constructor(
    @RemoteDataSource private val remoteDataSource: DogsReadableDataSource
) : DogsRepository {
    override suspend fun getDogs(): List<Dog> {
        return remoteDataSource.getDogs()
    }
}
