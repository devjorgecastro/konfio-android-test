package com.example.konfio.android.ui.screens.dogs

import com.example.konfio.android.R
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.domain.usecase.GetDogsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("DogsViewModel")
class DogsViewModelTest {

    @MockK
    private lateinit var getDogsUseCase: GetDogsUseCase
    private lateinit var viewModel: DogsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)
        coEvery { getDogsUseCase() } returns emptyList()
        viewModel = DogsViewModel(getDogsUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    @DisplayName("Initial state is correct")
    fun `initial state has empty values`() = runTest {
        // Given
        val expectedState = DogContract.State()

        // When - Initial state is loaded automatically
        val initialState = viewModel.state.first()

        // Then
        assertEquals(expectedState, initialState)
        coVerify(exactly = 0) { getDogsUseCase() }
        confirmVerified(getDogsUseCase)
    }

    @Test
    @DisplayName("LoadDogs updates state correctly on success")
    fun `given successful response when LoadDogs is called then updates state correctly`() = runTest {
        // Given
        val dogs = listOf(
            Dog(
                dogName = "Rex",
                description = "A brave dog",
                age = 5,
                image = "https://example.com/rex.jpg"
            )
        )
        coEvery { getDogsUseCase() } returns dogs

        // When - First get initial state to activate onStart
        val states = mutableListOf<DogContract.State>()
        val job = launch { viewModel.state.toList(states) }
        advanceTimeBy(500) // For animation delay
        advanceUntilIdle()

        // Then
        with(states.last()) {
            assertFalse(isLoading)
            assertFalse(isRefreshing)
            assertEquals(dogs, this.dogs)
            assertFalse(showEmptyState)
            assertNull(errorMessageRes)
        }

        coVerify(exactly = 1) { getDogsUseCase() }
        confirmVerified(getDogsUseCase)
        job.cancel()
    }

    @Test
    @DisplayName("LoadDogs shows empty state when no dogs")
    fun `given empty list when LoadDogs is called then shows empty state`() = runTest {
        // Given
        coEvery { getDogsUseCase() } returns emptyList()

        // When - First get initial state to activate onStart
        val states = mutableListOf<DogContract.State>()
        val job = launch { viewModel.state.toList(states) }
        advanceTimeBy(500) // For animation delay
        advanceUntilIdle()

        // Then
        with(states.last()) {
            assertFalse(isLoading)
            assertTrue(showEmptyState)
            assertTrue(dogs.isEmpty())
            assertNull(errorMessageRes)
        }

        coVerify(exactly = 1) { getDogsUseCase() }
        confirmVerified(getDogsUseCase)
        job.cancel()
    }

    @Test
    @DisplayName("LoadDogs shows error when fails")
    fun `given error when LoadDogs is called then shows error state`() = runTest {
        // Given
        coEvery { getDogsUseCase() } throws RuntimeException("Network error")

        // When - First get initial state to activate onStart
        val states = mutableListOf<DogContract.State>()
        val job = launch { viewModel.state.toList(states) }
        advanceTimeBy(500) // For animation delay
        advanceUntilIdle()

        // Then
        with(states.last()) {
            assertFalse(isLoading)
            assertEquals(R.string.server_connection_error, errorMessageRes)
            assertTrue(dogs.isEmpty())
            assertTrue(showEmptyState)
        }

        coVerify(exactly = 1) { getDogsUseCase() }
        confirmVerified(getDogsUseCase)
        job.cancel()
    }

    @Test
    @DisplayName("RefreshDogs updates state correctly")
    fun `given successful refresh when RefreshDogs is called then updates state correctly`() = runTest {
        // Given
        val dogs = listOf(
            Dog(
                dogName = "Max",
                description = "A playful dog",
                age = 3,
                image = "https://example.com/max.jpg"
            )
        )
        coEvery { getDogsUseCase() } returns dogs

        // When - First get initial state to activate onStart
        val states = mutableListOf<DogContract.State>()
        val job = launch { viewModel.state.toList(states) }
        viewModel.onEvent(DogsEvent.RefreshDogs)
        advanceTimeBy(500) // For animation delay
        advanceUntilIdle()

        // Then
        with(states.last()) {
            assertFalse(isLoading)
            assertFalse(isRefreshing)
            assertEquals(dogs, this.dogs)
            assertFalse(showEmptyState)
            assertNull(errorMessageRes)
        }

        coVerify(exactly = 2) { getDogsUseCase() }
        confirmVerified(getDogsUseCase)
        job.cancel()
    }

    @Test
    @DisplayName("SelectDog updates selected dog")
    fun `given dog when SelectDog is called then updates selected dog`() = runTest {
        // Given
        val dog = Dog(
            dogName = "Luna",
            description = "A loving dog",
            age = 2,
            image = "https://example.com/luna.jpg"
        )

        // When - First get initial state to activate onStart
        val states = mutableListOf<DogContract.State>()
        val job = launch { viewModel.state.toList(states) }
        advanceUntilIdle()
        viewModel.onEvent(DogsEvent.SelectDog(dog))
        advanceUntilIdle()

        // Then
        assertEquals(dog, states.last().selectedDog)
        coVerify(exactly = 1) { getDogsUseCase() }
        confirmVerified(getDogsUseCase)
        job.cancel()
    }

    @Test
    @DisplayName("DismissError clears error message")
    fun `given error state when DismissError is called then clears error message`() = runTest {
        // Given - First create an error state
        coEvery { getDogsUseCase() } throws RuntimeException("Network error")
        
        // When - First get initial state to activate onStart
        val states = mutableListOf<DogContract.State>()
        val job = launch { viewModel.state.toList(states) }
        advanceTimeBy(500)
        advanceUntilIdle()
        viewModel.onEvent(DogsEvent.DismissError)
        advanceUntilIdle()

        // Then
        assertNull(states.last().errorMessageRes)
        coVerify(exactly = 1) { getDogsUseCase() }
        confirmVerified(getDogsUseCase)
        job.cancel()
    }

    @Test
    @DisplayName("NavToDetail emits navigation effect")
    fun `given dog when NavToDetail is called then emits navigation effect`() = runTest {
        // Given
        val dog = Dog(
            dogName = "Rocky",
            description = "A strong dog",
            age = 4,
            image = "https://example.com/rocky.jpg"
        )

        // When - First get initial state to activate onStart
        val states = mutableListOf<DogContract.State>()
        val job = launch { viewModel.state.toList(states) }
        advanceUntilIdle()
        viewModel.onEvent(DogsEvent.NavToDetail(dog))
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.navEffect is Flow<DogContract.NavEffect>)
        coVerify(exactly = 1) { getDogsUseCase() }
        confirmVerified(getDogsUseCase)
        job.cancel()
    }
} 