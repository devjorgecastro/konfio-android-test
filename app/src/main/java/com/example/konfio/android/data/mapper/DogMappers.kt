package com.example.konfio.android.data.mapper

import com.example.konfio.android.data.local.entity.DogEntity
import com.example.konfio.android.data.remote.dto.DogDto
import com.example.konfio.android.domain.model.Dog

fun DogDto.toDog(): Dog = Dog(
    dogName = dogName,
    description = description,
    age = age,
    image = image
)

fun DogEntity.toDog(): Dog = Dog(
    dogName = dogName,
    description = description,
    age = age,
    image = image
)

fun Dog.toEntity(): DogEntity = DogEntity(
    dogName = dogName,
    description = description,
    age = age,
    image = image
) 