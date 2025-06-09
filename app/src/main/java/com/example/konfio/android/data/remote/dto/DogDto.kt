package com.example.konfio.android.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DogDto(
    @SerialName("dogName")
    val dogName: String,
    @SerialName("description")
    val description: String,
    @SerialName("age")
    val age: Int,
    @SerialName("image")
    val image: String
) 