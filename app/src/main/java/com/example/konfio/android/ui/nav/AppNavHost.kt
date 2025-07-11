@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.konfio.android.ui.nav

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.konfio.android.common.compose.CollectEffect
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.ui.screens.dogs.DogContract
import com.example.konfio.android.ui.screens.dogs.DogsScreen
import com.example.konfio.android.ui.screens.dogs.DogsViewModel
import com.example.konfio.android.ui.screens.dogs.detail.DogDetailContract
import com.example.konfio.android.ui.screens.dogs.detail.DogDetailScreen
import com.example.konfio.android.ui.screens.dogs.detail.DogsDetailViewModel

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    NavHost(
        navController = navHostController,
        startDestination = AppRoute.Dogs
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
    composable<AppRoute.Dogs> {

        val viewModel: DogsViewModel = hiltViewModel()
        CollectEffect(viewModel.navEffect) {
            when(it) {
                is DogContract.NavEffect.NavToDetail -> {
                    navController.navigate(
                        AppRoute.DogDetail(
                            imageUrl = it.dog.image,
                            name = it.dog.dogName,
                            description = it.dog.description,
                            age = it.dog.age
                        )
                    )
                }
            }
        }

        sharedTransitionScope.DogsScreen(
            animatedVisibilityScope = this,
            viewModel = viewModel
        )
    }
}

private fun NavGraphBuilder.dogDetail(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    composable<AppRoute.DogDetail> { backStackEntry ->

        val viewModel: DogsDetailViewModel = hiltViewModel()
        CollectEffect(viewModel.navEffect) {
            when(it) {
                DogDetailContract.NavEffect.NavToBack -> navController.popBackStack()
            }
        }

        val detail: AppRoute.DogDetail = backStackEntry.toRoute()
        sharedTransitionScope.DogDetailScreen(
            viewModel = viewModel,
            dog = Dog(
                dogName = detail.name,
                description = detail.description,
                age = detail.age,
                image = detail.imageUrl
            ),
            animatedVisibilityScope = this
        )
    }
}
