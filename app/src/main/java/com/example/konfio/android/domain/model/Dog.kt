package com.example.konfio.android.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dog(
    //@SerialName("dogName")
    val dogName: String,
    //@SerialName("description")
    val description: String,
    //@SerialName("age")
    val age: Int,
    //@SerialName("image")
    val image: String
)
