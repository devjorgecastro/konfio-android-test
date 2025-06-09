package com.example.konfio.android.di

import com.example.konfio.android.data.repository.DefaultDogsRepository
import com.example.konfio.android.domain.repository.DogsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDogsRepository(
        repository: DefaultDogsRepository
    ): DogsRepository = repository
} 