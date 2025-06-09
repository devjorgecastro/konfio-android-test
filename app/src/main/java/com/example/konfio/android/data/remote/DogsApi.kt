package com.example.konfio.android.data.remote

import com.example.konfio.android.data.remote.dto.DogDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import javax.inject.Inject

class DogsApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getDogs(): List<DogDto> {
        return client.get {
            url {
                appendPathSegments("api", "1151549092634943488")
            }
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
        }.body()
    }
}