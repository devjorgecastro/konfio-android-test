package com.example.konfio.android.data.repository

import com.example.konfio.android.data.datasource.DogsDataSource
import com.example.konfio.android.data.datasource.readable.DogsReadableDataSource
import com.example.konfio.android.domain.exception.DomainException
import com.example.konfio.android.domain.model.Dog
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("DefaultDogsRepository")
class DefaultDogsRepositoryTest {
    @MockK
    private lateinit var localDataSource: DogsDataSource
    @MockK
    private lateinit var remoteDataSource: DogsReadableDataSource
    private lateinit var repository: DefaultDogsRepository

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        repository = DefaultDogsRepository(localDataSource, remoteDataSource)
    }

    @Nested
    @DisplayName("getDogs")
    inner class GetDogs {

        @Test
        @DisplayName("Returns local data when local storage is not empty")
        fun `given non-empty local storage when getDogs is called then returns local data`() = runTest {
            // Given
            val localDogs = getFakeDogs()
            coEvery { localDataSource.getDogs() } returns localDogs

            // When
            val result = repository.getDogs()

            // Then
            assertEquals(localDogs, result)
            coVerify(exactly = 1) { localDataSource.getDogs() }
            coVerify(exactly = 0) {
                remoteDataSource.getDogs()
                localDataSource.saveDogs(any())
            }

            confirmVerified(localDataSource, remoteDataSource)
        }

        @Test
        @DisplayName("Fetches and saves remote data when local storage is empty")
        fun `given empty local storage when getDogs is called then fetches and saves remote data`() = runTest {
            // Given
            val remoteDogs = getFakeDogs()
            coEvery { localDataSource.getDogs() } returns emptyList()
            coEvery { remoteDataSource.getDogs() } returns remoteDogs
            coEvery { localDataSource.saveDogs(remoteDogs) } returns Unit

            // When
            val result = repository.getDogs()

            // Then
            assertEquals(remoteDogs, result)
            coVerify(exactly = 1) {
                localDataSource.getDogs()
                remoteDataSource.getDogs()
                localDataSource.saveDogs(remoteDogs)
            }
            coVerifyOrder {
                localDataSource.getDogs()
                remoteDataSource.getDogs()
                localDataSource.saveDogs(remoteDogs)
            }

            confirmVerified(localDataSource, remoteDataSource)
        }

        @Test
        @DisplayName("Propagates remote error when local storage is empty and remote fetch fails")
        fun `given empty local storage and remote error when getDogs is called then throws exception`() = runTest {
            // Given
            val exception = RuntimeException("Network error")
            coEvery { localDataSource.getDogs() } returns emptyList()
            coEvery { remoteDataSource.getDogs() } throws exception

            // When / Then
            val thrown = assertThrows<DomainException> {
                repository.getDogs()
            }

            assertEquals("Network error", thrown.message)
            coVerify(exactly = 1) {
                localDataSource.getDogs()
                remoteDataSource.getDogs()
            }
            confirmVerified(localDataSource, remoteDataSource)
        }

        @Test
        @DisplayName("Propagates local error when local storage access fails")
        fun `given local storage error when getDogs is called then throws exception`() = runTest {
            // Given
            val exception = RuntimeException("Database error")
            coEvery { localDataSource.getDogs() } throws exception

            // When / Then
            val thrown = assertThrows<DomainException> {
                repository.getDogs()
            }

            assertEquals("Database error", thrown.message)
            coVerify(exactly = 1) { localDataSource.getDogs() }
            confirmVerified(localDataSource, remoteDataSource)
        }

        @Test
        @DisplayName("Propagates save error when saving remote data fails")
        fun `given empty local storage and save error when getDogs is called then throws exception`() = runTest {
            // Given
            val remoteDogs = getFakeDogs()
            val exception = RuntimeException("Save error")
            coEvery { localDataSource.getDogs() } returns emptyList()
            coEvery { remoteDataSource.getDogs() } returns remoteDogs
            coEvery { localDataSource.saveDogs(remoteDogs) } throws exception

            // When / Then
            val thrown = assertThrows<DomainException> {
                repository.getDogs()
            }

            assertEquals("Save error", thrown.message)
            coVerify(exactly = 1) {
                localDataSource.getDogs()
                remoteDataSource.getDogs()
                localDataSource.saveDogs(remoteDogs)
            }
            coVerifyOrder {
                localDataSource.getDogs()
                remoteDataSource.getDogs()
                localDataSource.saveDogs(remoteDogs)
            }
            confirmVerified(localDataSource, remoteDataSource)
        }
    }

    private fun getFakeDogs(): List<Dog> {
        return listOf(
            Dog(
                dogName = "Rex",
                description = "A friendly dog",
                age = 5,
                image = "https://example.com/rex.jpg"
            ),
            Dog(
                dogName = "Max",
                description = "A playful dog",
                age = 3,
                image = "https://example.com/max.jpg"
            )
        )
    }
} 