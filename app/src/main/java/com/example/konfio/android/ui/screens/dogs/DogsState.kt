package com.example.konfio.android.ui.screens.dogs

import com.example.konfio.android.domain.model.Dog

data class DogsState(
    val dogs: List<Dog> = emptyList(),
    val selectedDog: Dog? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) 