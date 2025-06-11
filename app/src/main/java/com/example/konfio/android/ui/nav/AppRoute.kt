package com.example.konfio.android.ui.nav

import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable
    object Dogs : AppRoute

    @Serializable
    data class DogDetail(
        val imageUrl: String,
        val name: String,
        val description: String,
        val age: Int
    ) : AppRoute
}
