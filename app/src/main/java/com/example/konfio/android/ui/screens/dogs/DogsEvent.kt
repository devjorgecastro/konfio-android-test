package com.example.konfio.android.ui.screens.dogs

import com.example.konfio.android.domain.model.Dog

sealed interface DogsEvent {
    data object LoadDogs : DogsEvent
    data object RefreshDogs : DogsEvent
    data class SelectDog(val dog: Dog?) : DogsEvent
    data object DismissError : DogsEvent
} 