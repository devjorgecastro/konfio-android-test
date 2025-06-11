package com.example.konfio.android.data.remote

import com.example.konfio.android.data.remote.dto.DogDto
import com.example.konfio.android.domain.model.Dog
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals

@DisplayName("RemoteDogsDataSource")
class RemoteDogsDataSourceTest {

    private lateinit var api: DogsApi
    private lateinit var dataSource: RemoteDogsDataSource

    @BeforeEach
    fun setup() {
        api = mockk()
        dataSource = RemoteDogsDataSource(api)
    }

    @Nested
    @DisplayName("getDogs")
    inner class GetDogs {

        @Test
        @DisplayName("Returns mapped dog list when API responds successfully")
        fun `given successful API response, when getDogs is called, then returns mapped dog list`() = runTest {
            // Given
            val dogDtoList = getFakeDogDtoList()
            val expectedDogs = listOf(
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
            coEvery { api.getDogs() } returns dogDtoList

            // When
            val result = dataSource.getDogs()

            // Then
            coVerify(exactly = 1) { api.getDogs() }
            assertEquals(expectedDogs, result)
            confirmVerified(api)
        }

        @Test
        @DisplayName("Throws exception when API fails")
        fun `given API failure, when getDogs is called, then throws exception`() = runTest {
            // Given
            val exception = RuntimeException("Network error")
            coEvery { api.getDogs() } throws exception

            // When / Then
            val thrown = assertThrows<RuntimeException> {
                dataSource.getDogs()
            }

            assertEquals("Network error", thrown.message)
            coVerify(exactly = 1) { api.getDogs() }
            confirmVerified(api)
        }

        @Test
        @DisplayName("Returns empty list when API returns empty list")
        fun `given empty API response when getDogs is called then returns empty list`() = runTest {
            // Given
            coEvery { api.getDogs() } returns emptyList()

            // When
            val result = dataSource.getDogs()

            // Then
            assert(result.isEmpty())
            coVerify(exactly = 1) { api.getDogs() }
            confirmVerified(api)
        }
    }

    private fun getFakeDogDtoList(): List<DogDto> {
        return listOf(
            DogDto(
                dogName = "Rex",
                description = "A friendly dog",
                age = 5,
                image = "https://example.com/rex.jpg"
            ),
            DogDto(
                dogName = "Max",
                description = "A playful dog",
                age = 3,
                image = "https://example.com/max.jpg"
            )
        )
    }
}
