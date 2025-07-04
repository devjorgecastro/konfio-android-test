package com.example.konfio.android.data.datasource

import com.example.konfio.android.data.datasource.readable.DogsReadableDataSource
import com.example.konfio.android.data.datasource.writable.DogsWritableDataSource
import com.example.konfio.android.domain.model.Dog

interface DogsDataSource: DogsReadableDataSource, DogsWritableDataSource {
    override suspend fun getDogs(): List<Dog>
    override suspend fun saveDogs(dogs: List<Dog>)
}
