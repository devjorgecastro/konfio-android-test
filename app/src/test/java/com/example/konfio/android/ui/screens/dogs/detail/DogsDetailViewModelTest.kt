package com.example.konfio.android.ui.screens.dogs.detail

import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("DogsDetailViewModel")
class DogsDetailViewModelTest {

    private lateinit var viewModel: DogsDetailViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)
        viewModel = DogsDetailViewModel()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    @DisplayName("NavToBack emits navigation effect")
    fun `when NavToBack is called then emits navigation effect`() = runTest {
        // Given
        val effects = mutableListOf<DogDetailContract.NavEffect>()
        val job = launch { viewModel.navEffect.toList(effects) }

        // When
        viewModel.onEvent(DogDetailEvent.NavToBack)
        advanceUntilIdle()

        // Then
        assertEquals(DogDetailContract.NavEffect.NavToBack, effects.last())
        job.cancel()
    }
} 