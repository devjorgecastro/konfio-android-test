package com.example.konfio.android.data.local

import com.example.konfio.android.data.datasource.DogsDataSource
import com.example.konfio.android.data.local.dao.DogsDao
import com.example.konfio.android.data.mapper.toDog
import com.example.konfio.android.data.mapper.toEntity
import com.example.konfio.android.domain.model.Dog
import javax.inject.Inject

class LocalDogsDataSource @Inject constructor(
    private val dogsDao: DogsDao
): DogsDataSource {

    override suspend fun getDogs(): List<Dog> {
        return dogsDao.getDogs().map { it.toDog() }
    }

    override suspend fun saveDogs(dogs: List<Dog>) {
        dogsDao.insertDogs(dogs.map { it.toEntity() })
    }
}
