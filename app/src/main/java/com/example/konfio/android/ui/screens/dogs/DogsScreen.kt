@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)

package com.example.konfio.android.ui.screens.dogs

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.ui.components.AnimatedDogDetailComponent
import com.example.konfio.android.ui.components.DogItem
import com.example.konfio.android.ui.components.DogTopBar
import com.example.konfio.android.ui.components.EmptyStateView
import com.example.konfio.android.ui.components.SnackbarView
import com.example.konfio.android.ui.theme.DogsTheme

private object Values {
    val HorizontalPadding = 16.dp
    val VerticalItemSpacingPortrait = 32.dp
    val VerticalItemSpacingLandscape = 16.dp
    const val MinDogsToDistributeSpace = 4
}

@Composable
fun SharedTransitionScope.DogsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: DogsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DogsScreenContent(
        state = state,
        animatedVisibilityScope = animatedVisibilityScope,
        onDispatchEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SharedTransitionScope.DogsScreenContent(
    state: DogContract.State = DogContract.State(),
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    onDispatchEvent: (DogsEvent) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                DogTopBar(onDispatchEvent = onDispatchEvent)
            }
        ) { paddingValues ->
            val pullToRefreshState = rememberPullToRefreshState()
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                state = pullToRefreshState,
                onRefresh = { onDispatchEvent(DogsEvent.RefreshDogs) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = state.isRefreshing,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        state = pullToRefreshState
                    )
                }
            ) {
                DogsContent(
                    state = state,
                    animatedVisibilityScope = animatedVisibilityScope,
                    onDispatchEvent = onDispatchEvent
                )
            }
        }

        state.selectedDog?.let { dog ->
            AnimatedDogDetailComponent(
                dog = dog,
                onDismiss = { onDispatchEvent(DogsEvent.SelectDog(null)) }
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (state.showEmptyState) {
            EmptyStateView()
        }

        state.errorMessageRes?.let { error ->
            SnackbarView(
                description = stringResource(error),
                onDispatchEvent = onDispatchEvent
            )
        }
    }
}

@Composable
private fun SharedTransitionScope.DogsContent(
    state: DogContract.State = DogContract.State(),
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    onDispatchEvent: (DogsEvent) -> Unit = {}
) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Values.VerticalItemSpacingPortrait),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Values.HorizontalPadding),
            ) {
                items(state.dogs) { dog ->
                    DogItem(
                        dog = dog,
                        onDogClick = { onDispatchEvent(DogsEvent.SelectDog(dog)) }
                    )
                }
            }
        }
        else -> {
            LazyRow(
                horizontalArrangement = if (state.dogs.size <= Values.MinDogsToDistributeSpace) {
                    Arrangement.SpaceBetween
                } else {
                    Arrangement.spacedBy(Values.VerticalItemSpacingLandscape)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Values.HorizontalPadding),
            ) {
                items(state.dogs) { dog ->
                    DogItem(
                        dog = dog,
                        animatedVisibilityScope = animatedVisibilityScope,
                        onDogClick = { onDispatchEvent(DogsEvent.NavToDetail(dog)) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyStateViewPreview() {
    DogsTheme {
        EmptyStateView()
    }
}

@Preview
@Composable
private fun DogsScreenPreview() {
    DogsTheme {
        SharedTransitionLayout {
            DogsScreenContent(
                state = DogContract.State(
                    dogs = listOf(
                        Dog(
                            dogName = "Fox",
                            description = "Fox description",
                            age = 3,
                            image = "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"
                        ),
                        Dog(
                            dogName = "Pepito",
                            description = "Pepito description",
                            age = 4,
                            image = "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"
                        )
                    )
                )
            )
        }
    }
}
