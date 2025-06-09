package com.example.konfio.android.data.exception.strategies

import com.example.konfio.android.domain.exception.DomainException

interface DataSideEffectStrategy {
    val managedExceptions: List<Class<out Throwable>>
        get() = emptyList()

    fun getDomainException(error: Throwable, data: Any?): DomainException
    fun hasManagedError(error: Throwable): Boolean
}