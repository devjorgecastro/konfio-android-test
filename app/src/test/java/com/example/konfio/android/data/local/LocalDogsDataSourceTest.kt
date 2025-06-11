package com.example.konfio.android.data.local

import com.example.konfio.android.data.local.dao.DogsDao
import com.example.konfio.android.data.local.entity.DogEntity
import com.example.konfio.android.domain.model.Dog
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("LocalDogsDataSource")
class LocalDogsDataSourceTest {

    private lateinit var dogsDao: DogsDao
    private lateinit var dataSource: LocalDogsDataSource

    @BeforeEach
    fun setup() {
        dogsDao = mockk()
        dataSource = LocalDogsDataSource(dogsDao)
    }

    @Nested
    @DisplayName("getDogs")
    inner class GetDogs {

        @Test
        @DisplayName("Returns mapped dog list when database has data")
        fun `given database with data when getDogs is called then returns mapped dog list`() = runTest {
            // Given
            val dogEntities = getFakeDogEntities()
            val expectedDogs = getFakeDogs()
            coEvery { dogsDao.getDogs() } returns dogEntities

            // When
            val result = dataSource.getDogs()

            // Then
            assertEquals(expectedDogs, result)
            coVerify(exactly = 1) { dogsDao.getDogs() }
            confirmVerified(dogsDao)
        }

        @Test
        @DisplayName("Returns empty list when database is empty")
        fun `given empty database when getDogs is called then returns empty list`() = runTest {
            // Given
            coEvery { dogsDao.getDogs() } returns emptyList()

            // When
            val result = dataSource.getDogs()

            // Then
            assert(result.isEmpty())
            coVerify(exactly = 1) { dogsDao.getDogs() }
            confirmVerified(dogsDao)
        }

        @Test
        @DisplayName("Throws exception when database access fails")
        fun `given database error when getDogs is called then throws exception`() = runTest {
            // Given
            val exception = RuntimeException("Database access error")
            coEvery { dogsDao.getDogs() } throws exception

            // When / Then
            val thrown = assertThrows<RuntimeException> {
                dataSource.getDogs()
            }

            assertEquals("Database access error", thrown.message)
            coVerify(exactly = 1) { dogsDao.getDogs() }
            confirmVerified(dogsDao)
        }
    }

    @Nested
    @DisplayName("saveDogs")
    inner class SaveDogs {

        @Test
        @DisplayName("Successfully saves dogs to database")
        fun `given valid dogs list when saveDogs is called then saves to database`() = runTest {
            // Given
            val dogs = getFakeDogs()
            val expectedEntities = getFakeDogEntities()
            coEvery { dogsDao.insertDogs(expectedEntities) } returns Unit

            // When
            dataSource.saveDogs(dogs)

            // Then
            coVerify(exactly = 1) { dogsDao.insertDogs(expectedEntities) }
            confirmVerified(dogsDao)
        }

        @Test
        @DisplayName("Successfully saves empty list to database")
        fun `given empty dogs list when saveDogs is called then saves empty list`() = runTest {
            // Given
            coEvery { dogsDao.insertDogs(emptyList()) } returns Unit

            // When
            dataSource.saveDogs(emptyList())

            // Then
            coVerify(exactly = 1) { dogsDao.insertDogs(emptyList()) }
            confirmVerified(dogsDao)
        }

        @Test
        @DisplayName("Throws exception when database save fails")
        fun `given database error when saveDogs is called then throws exception`() = runTest {
            // Given
            val dogs = getFakeDogs()
            val expectedEntities = getFakeDogEntities()
            val exception = RuntimeException("Database write error")
            coEvery { dogsDao.insertDogs(expectedEntities) } throws exception

            // When / Then
            val thrown = assertThrows<RuntimeException> {
                dataSource.saveDogs(dogs)
            }

            assertEquals("Database write error", thrown.message)
            coVerify(exactly = 1) { dogsDao.insertDogs(expectedEntities) }
            confirmVerified(dogsDao)
        }
    }

    private fun getFakeDogEntities(): List<DogEntity> {
        return listOf(
            DogEntity(
                dogName = "Rex",
                description = "A friendly dog",
                age = 5,
                image = "https://example.com/rex.jpg"
            ),
            DogEntity(
                dogName = "Max",
                description = "A playful dog",
                age = 3,
                image = "https://example.com/max.jpg"
            )
        )
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