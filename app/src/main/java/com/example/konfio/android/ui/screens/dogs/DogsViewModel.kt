package com.example.konfio.android.ui.screens.dogs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.domain.usecase.GetDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val getDogsUseCase: GetDogsUseCase
) : ViewModel() {
    val dogs: List<Dog> = getDogsUseCase()
    
    var selectedDog: Dog? by mutableStateOf(null)
        private set
        
    fun onDogSelected(dog: Dog) {
        selectedDog = dog
    }
    
    fun onDogDismissed() {
        selectedDog = null
    }
} 