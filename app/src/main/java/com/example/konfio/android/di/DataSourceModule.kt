package com.example.konfio.android.di

import com.example.konfio.android.data.datasource.readable.DogsReadableDataSource
import com.example.konfio.android.data.datasource.RemoteDogsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    @RemoteDataSource
    abstract fun bindRemoteDataSource(
        remoteDataSource: RemoteDogsDataSource
    ): DogsReadableDataSource
}
