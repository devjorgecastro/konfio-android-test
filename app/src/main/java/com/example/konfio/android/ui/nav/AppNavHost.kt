@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.konfio.android.ui.nav

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.ui.screens.dogs.DogsScreen
import com.example.konfio.android.ui.screens.dogs.detail.DogDetailScreen
import kotlinx.serialization.Serializable

@Serializable
object DogsScreen

// Define a profile route that takes an ID
@Serializable
data class DogDetail(
    val imageUrl: String,
    val name: String,
    val description: String,
    val age: Int
)

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    NavHost(
        navController = navHostController,
        startDestination = DogsScreen
    ) {
        dogsScreen(
            navController = navHostController,
            sharedTransitionScope = sharedTransitionScope
        )
        dogDetail(
            navController = navHostController,
            sharedTransitionScope = sharedTransitionScope
        )
    }
}

private fun NavGraphBuilder.dogsScreen(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    composable<DogsScreen> {
        sharedTransitionScope.DogsScreen(
            animatedVisibilityScope = this,
            onItemClick = { dog ->
                navController.navigate(
                    DogDetail(
                        imageUrl = dog.image,
                        name = dog.dogName,
                        description = dog.description,
                        age = dog.age
                    )
                )
            }
        )
    }
}

private fun NavGraphBuilder.dogDetail(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    composable<DogDetail> { backStackEntry ->
        val dog: DogDetail = backStackEntry.toRoute()
        sharedTransitionScope.DogDetailScreen(
            Dog(
                dogName = dog.name,
                description = dog.description,
                age = dog.age,
                image = dog.imageUrl
            ),
            animatedVisibilityScope = this
        )
    }
}