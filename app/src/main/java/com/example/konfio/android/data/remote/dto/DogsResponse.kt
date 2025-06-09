package com.example.konfio.android.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DogsResponse(
    val dogs: List<DogDto>
) 