package com.example.konfio.android.di

import android.content.Context
import androidx.room.Room
import com.example.konfio.android.data.local.DogsDatabase
import com.example.konfio.android.data.local.dao.DogsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDogsDatabase(
        @ApplicationContext context: Context
    ): DogsDatabase {
        return Room.databaseBuilder(
            context,
            DogsDatabase::class.java,
            "dogs.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDogsDao(database: DogsDatabase): DogsDao {
        return database.dogsDao
    }
} 