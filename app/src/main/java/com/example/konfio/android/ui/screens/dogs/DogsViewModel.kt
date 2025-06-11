package com.example.konfio.android.ui.screens.dogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.konfio.android.R
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.domain.usecase.GetDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.annotation.StringRes

sealed interface DogContract {

    data class State(
        val dogs: List<Dog> = emptyList(),
        val selectedDog: Dog? = null,
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val showEmptyState: Boolean = false,
        @StringRes val errorMessageRes: Int? = null
    )

    sealed interface NavEffect {
        data class NavToDetail(val dog: Dog) : NavEffect
    }
}

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val getDogsUseCase: GetDogsUseCase
) : ViewModel() {

    private val mutableState = MutableStateFlow(DogContract.State())
    val state: StateFlow<DogContract.State> = mutableState
        .onStart {
            onEvent(DogsEvent.LoadDogs)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DogContract.State()
        )

    private val mutableNavEffect: Channel<DogContract.NavEffect> = Channel()
    val navEffect = mutableNavEffect.receiveAsFlow()

    fun onEvent(event: DogsEvent) {
        when (event) {
            is DogsEvent.LoadDogs -> loadDogs()
            is DogsEvent.RefreshDogs -> refreshDogs()
            is DogsEvent.SelectDog -> selectDog(event.dog)
            is DogsEvent.DismissError -> dismissError()
            is DogsEvent.NavToDetail -> navToDetailScreen(event.dog)
        }
    }

    private fun loadDogs() {
        fetchDogs(isInitialLoad = true)
    }

    private fun refreshDogs() {
        fetchDogs(isInitialLoad = false)
    }

    private suspend fun applyDelayForIndicatorAnimation() {
        delay(500)
    }

    private fun fetchDogs(isInitialLoad: Boolean) {
        viewModelScope.launch {

            mutableState.update {
                it.copy(
                    isLoading = isInitialLoad,
                    isRefreshing = !isInitialLoad,
                    errorMessageRes = null
                )
            }

            try {
                val dogs = getDogsUseCase()
                applyDelayForIndicatorAnimation()
                mutableState.update {
                    it.copy(
                        dogs = dogs,
                        isLoading = false,
                        isRefreshing = false,
                        showEmptyState = dogs.isEmpty()
                    )
                }
            } catch (e: Exception) {
                applyDelayForIndicatorAnimation()
                mutableState.update {
                    it.copy(
                        errorMessageRes = R.string.server_connection_error,
                        isLoading = false,
                        isRefreshing = false,
                        showEmptyState = it.dogs.isEmpty()
                    )
                }
            }
        }
    }

    private fun selectDog(dog: Dog?) {
        mutableState.update { it.copy(selectedDog = dog) }
    }

    private fun dismissError() {
        mutableState.update { it.copy(errorMessageRes = null) }
    }

    private fun navToDetailScreen(dog: Dog) {
        viewModelScope.launch {
            mutableNavEffect.send(DogContract.NavEffect.NavToDetail(dog))
        }
    }
}
