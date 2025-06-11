package com.example.konfio.android.ui.screens.dogs.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface DogDetailContract {
    sealed interface NavEffect {
        data object NavToBack : NavEffect
    }
}

@HiltViewModel
class DogsDetailViewModel @Inject constructor() : ViewModel() {

    private val mutableNavEffect: Channel<DogDetailContract.NavEffect> = Channel()
    val navEffect = mutableNavEffect.receiveAsFlow()

    fun onEvent(event: DogDetailEvent) {
        when (event) {
            is DogDetailEvent.NavToBack -> navToBack()
        }
    }

    private fun navToBack() {
        viewModelScope.launch {
            mutableNavEffect.send(DogDetailContract.NavEffect.NavToBack)
        }
    }
} 