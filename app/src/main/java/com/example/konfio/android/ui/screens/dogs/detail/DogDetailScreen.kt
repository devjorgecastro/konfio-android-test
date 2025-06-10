@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.konfio.android.ui.screens.dogs.detail

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.ui.components.DogImage
import com.example.konfio.android.ui.components.DogInfo

private const val IMAGE_WIDTH = 350
private const val IMAGE_HEIGHT = 550

@Composable
fun SharedTransitionScope.DogDetailScreen(
    dog: Dog,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val configuration = LocalConfiguration.current.orientation
    when (configuration) {
        Configuration.ORIENTATION_PORTRAIT -> PortraitContent(
            dog = dog,
            animatedVisibilityScope = animatedVisibilityScope
        )
        else -> LandscapeContent(
            dog = dog,
            animatedVisibilityScope = animatedVisibilityScope
        )
    }
}

@Composable
private fun SharedTransitionScope.PortraitContent(
    dog: Dog,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DogImage(
            dog = dog,
            modifier = Modifier
                .height(IMAGE_HEIGHT.dp)
                .fillMaxWidth()
                .sharedElement(
                    sharedContentState = rememberSharedContentState(key = dog.dogName),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        )

        DogInfo(
            dog = dog,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
private fun SharedTransitionScope.LandscapeContent(
    dog: Dog,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DogImage(
            dog = dog,
            modifier = Modifier
                .width(IMAGE_WIDTH.dp)
                .fillMaxHeight()
                .sharedElement(
                    sharedContentState = rememberSharedContentState(key = dog.dogName),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        )

        DogInfo(
            dog = dog,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
