package com.example.konfio.android.data.remote

import com.example.konfio.android.data.remote.dto.DogDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class DogsApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getDogs(): List<DogDto> {
        // La API devuelve directamente un array, así que podemos deserializarlo como List<DogDto>
        return client.get("https://jsonblob.com/api/1151549092634943488").body()
    }
}