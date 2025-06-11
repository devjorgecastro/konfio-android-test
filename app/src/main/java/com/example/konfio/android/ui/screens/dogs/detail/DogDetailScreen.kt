@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.konfio.android.ui.screens.dogs.detail

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.konfio.android.domain.model.Dog
import com.example.konfio.android.ui.components.DogImage
import com.example.konfio.android.ui.components.DogInfo
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color

private const val IMAGE_WIDTH = 350
private const val IMAGE_HEIGHT = 550

@Composable
fun SharedTransitionScope.DogDetailScreen(
    dog: Dog,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    var showBackIcon by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(500)
        showBackIcon = true
    }

    Box {
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

        if (showBackIcon) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(32.dp)
                    .size(48.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape,
                        clip = false
                    )
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
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
