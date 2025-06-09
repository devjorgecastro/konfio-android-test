package com.example.konfio.android.ui.screens.dogs

import androidx.annotation.StringRes
import com.example.konfio.android.domain.model.Dog

data class DogsState(
    val dogs: List<Dog> = emptyList(),
    val selectedDog: Dog? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val showEmptyState: Boolean = false,
    @StringRes val errorMessageRes: Int? = null
)
