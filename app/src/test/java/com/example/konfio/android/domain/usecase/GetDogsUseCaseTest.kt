package com.example.konfio.android.domain.usecase

import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.domain.repository.DogsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("GetDogsUseCase")
class GetDogsUseCaseTest {

    private lateinit var repository: DogsRepository
    private lateinit var useCase: GetDogsUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        useCase = GetDogsUseCase(repository)
    }

    @Test
    @DisplayName("invoke returns list of dogs from repository")
    fun `given repository returns dogs when invoke is called then returns same list`() = runTest {
        // Given
        val expectedDogs = listOf(
            Dog(
                dogName = "Rex",
                description = "Un perro valiente",
                age = 5,
                image = "https://example.com/rex.jpg"
            ),
            Dog(
                dogName = "Max",
                description = "Un perro juguetón",
                age = 3,
                image = "https://example.com/max.jpg"
            )
        )
        coEvery { repository.getDogs() } returns expectedDogs

        // When
        val result = useCase()

        // Then
        assertEquals(expectedDogs, result)
        coVerify(exactly = 1) { repository.getDogs() }
        confirmVerified(repository)
    }

    @Test
    @DisplayName("invoke returns empty list when repository returns empty")
    fun `given repository returns empty list when invoke is called then returns empty list`() = runTest {
        // Given
        val expectedDogs = emptyList<Dog>()
        coEvery { repository.getDogs() } returns expectedDogs

        // When
        val result = useCase()

        // Then
        assertEquals(expectedDogs, result)
        coVerify(exactly = 1) { repository.getDogs() }
        confirmVerified(repository)
    }

    @Test
    @DisplayName("invoke propagates repository exception")
    fun `given repository throws exception when invoke is called then propagates exception`() = runTest {
        // Given
        val expectedException = RuntimeException("Error al obtener perros")
        coEvery { repository.getDogs() } throws expectedException

        // When/Then
        val exception = assertThrows<RuntimeException> {
            useCase()
        }
        assertEquals(expectedException.message, exception.message)
        coVerify(exactly = 1) { repository.getDogs() }
        confirmVerified(repository)
    }
}
