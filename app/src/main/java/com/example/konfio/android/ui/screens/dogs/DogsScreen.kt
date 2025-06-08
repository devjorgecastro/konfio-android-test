package com.example.konfio.android.ui.screens.dogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.konfio.android.R
import com.example.konfio.android.ui.components.DogDetail
import com.example.konfio.android.ui.components.DogItem

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DogsScreen(
    viewModel: DogsViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.dogs_we_love)) }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(40.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                items(viewModel.dogs) { dog ->
                    DogItem(
                        dog = dog,
                        onDogClick = { viewModel.onDogSelected(it) }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = viewModel.selectedDog != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            viewModel.selectedDog?.let { dog ->
                DogDetail(
                    dog = dog,
                    onDismiss = { viewModel.onDogDismissed() }
                )
            }
        }
    }
}
