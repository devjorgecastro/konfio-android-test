package com.example.konfio.android.di

import com.example.konfio.android.data.datasource.DogsDataSource
import com.example.konfio.android.data.datasource.readable.DogsReadableDataSource
import com.example.konfio.android.data.local.LocalDogsDataSource
import com.example.konfio.android.data.remote.RemoteDogsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDogsQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDogsQualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    @RemoteDogsQualifier
    abstract fun bindRemoteDataSource(
        remoteDataSource: RemoteDogsDataSource
    ): DogsReadableDataSource

    @Binds
    @Singleton
    @LocalDogsQualifier
    abstract fun bindLocalDataSource(
        remoteDataSource: LocalDogsDataSource
    ): DogsDataSource
}
