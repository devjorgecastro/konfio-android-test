package com.example.konfio.android.di

import android.util.Log
import com.example.konfio.android.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = getDefaultHttpClient()

    private fun getDefaultHttpClient(): HttpClient {
        return HttpClient(Android) {
            defaultRequest {
                url(BuildConfig.BASE_URL)
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            engine {
                connectTimeout = 100_000
                socketTimeout = 100_000
            }
            if (BuildConfig.DEBUG) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.d("ktor log: ", message)
                        }
                    }
                    level = LogLevel.BODY
                }
            }
        }
    }
}