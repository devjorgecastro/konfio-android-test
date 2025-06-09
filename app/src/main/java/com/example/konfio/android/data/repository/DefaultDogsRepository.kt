package com.example.konfio.android.data.repository

import com.example.konfio.android.data.datasource.DogsDataSource
import com.example.konfio.android.data.datasource.readable.DogsReadableDataSource
import com.example.konfio.android.di.LocalDogsQualifier
import com.example.konfio.android.di.RemoteDogsQualifier
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.domain.repository.DogsRepository
import javax.inject.Inject

class DefaultDogsRepository @Inject constructor(
    @LocalDogsQualifier private val localDataSource: DogsDataSource,
    @RemoteDogsQualifier private val remoteDataSource: DogsReadableDataSource
) : DogsRepository {

    override suspend fun getDogs(): List<Dog> {
        val localDogs = localDataSource.getDogs()
        return if (localDogs.isEmpty()) {
            remoteDataSource.getDogs().also { dogs ->
                localDataSource.saveDogs(dogs)
            }
        } else {
            localDogs
        }
    }
}
