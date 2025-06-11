package com.example.konfio.android.data.exception.strategies

import android.database.sqlite.SQLiteAccessPermException
import com.example.konfio.android.domain.exception.DomainException
import com.example.konfio.android.domain.exception.GeneralDatabaseException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("DogsErrorStrategy")
class DogsErrorStrategyTest {

    private lateinit var strategy: DogsErrorStrategy

    @BeforeEach
    fun setup() {
        strategy = DogsErrorStrategy()
    }

    @Test
    @DisplayName("hasManagedError returns true when error is in managedExceptions list")
    fun `given SQLiteAccessPermException when hasManagedError is called then returns true`() {
        // Given
        val error = SQLiteAccessPermException("Access denied")

        // When
        val result = strategy.hasManagedError(error)

        // Then
        assertTrue(result)
    }

    @Test
    @DisplayName("hasManagedError returns false when error is not in managedExceptions list")
    fun `given unknown exception when hasManagedError is called then returns false`() {
        // Given
        val error = IllegalArgumentException("Invalid input")

        // When
        val result = strategy.hasManagedError(error)

        // Then
        assertFalse(result)
    }

    @Test
    @DisplayName("getDomainException returns GeneralDatabaseException for managed exception")
    fun `given managed error when getDomainException is called then returns GeneralDatabaseException`() {
        // Given
        val error = SQLiteAccessPermException("Access denied")
        val data = "Extra info"

        // When
        val exception = strategy.getDomainException(error, data)

        // Then
        assertTrue(exception is DomainException)
        assertEquals(data, (exception as GeneralDatabaseException).localData)
    }

    @Test
    @DisplayName("getDomainException returns DomainException for unhandled exception")
    fun `given unhandled error when getDomainException is called then returns DomainException`() {
        // Given
        val error = IllegalStateException("Unexpected state")
        val data = "Extra context"

        // When
        val exception = strategy.getDomainException(error, data)

        // Then
        assertTrue(exception is DomainException)
        assertFalse(exception is GeneralDatabaseException)
        assertEquals("Unexpected state", exception.message)
    }
}
