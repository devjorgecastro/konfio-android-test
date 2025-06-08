package com.example.konfio.android.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.konfio.android.domain.model.Dog
import kotlinx.coroutines.delay
import androidx.compose.runtime.State

private const val imageHeight = 500f
private const val contentHeight = 200f
private const val initialContentOffsetY = 100f

@Composable
fun DogDetail(
    dog: Dog,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val updatedOnDismiss by rememberUpdatedState(onDismiss)
    var shouldShowInfo by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200)
        shouldShowInfo = true
    }

    val imageOffset = animatedOffsetY(if (shouldShowInfo) -contentHeight/2 else 0f)
    val infoOffset = animatedOffsetY(if (shouldShowInfo) imageHeight-contentHeight/2 else initialContentOffsetY)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .clickable {
                shouldShowInfo = false
                updatedOnDismiss()
            },
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth(0.85f)) {
            InfoCard(
                dog = dog,
                offsetY = infoOffset.value.dp
            )
            ImageCard(
                dog = dog,
                offsetY = imageOffset.value.dp
            )
        }
    }
}

@Composable
private fun animatedOffsetY(targetValue: Float): State<Float> {
    return animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(durationMillis = 500),
        label = "animated_offset_y"
    )
}

@Composable
private fun InfoCard(dog: Dog, offsetY: Dp) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(contentHeight.dp)
            .offset(y = offsetY),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = dog.dogName,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = dog.description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun ImageCard(dog: Dog, offsetY: Dp) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight.dp)
            .offset(y = offsetY),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        AsyncImage(
            model = dog.image,
            contentDescription = dog.dogName,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
