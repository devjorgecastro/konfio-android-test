package com.example.konfio.android.ui.screens.dogs.detail

sealed interface DogDetailEvent {
    data object NavToBack : DogDetailEvent
}
