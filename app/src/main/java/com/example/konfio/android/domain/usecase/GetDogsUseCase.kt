package com.example.konfio.android.domain.usecase

import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.domain.repository.DogsRepository
import javax.inject.Inject

class GetDogsUseCase @Inject constructor(
    private val repository: DogsRepository
) {
    suspend operator fun invoke(): List<Dog> {
        return repository.getDogs()
    }
}
