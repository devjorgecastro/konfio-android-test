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
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

private const val IMAGE_HEIGHT = 500f
private const val CONTENT_HEIGHT = 200f
private const val INITIAL_CONTENT_OFFSET_Y = 100f
private const val IMAGE_CARD_WIDTH_PERCENT = 0.85f
private const val OVERLAY_ALPHA = 0.7f
private const val INFO_APPEAR_DELAY_MS = 200L
private const val INFO_DISMISS_DELAY_MS = 300L
private const val CARD_CORNER_RADIUS_DP = 16
private const val ANIMATION_DURATION_MS = 500

@Composable
fun DogDetail(
    dog: Dog,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val updatedOnDismiss by rememberUpdatedState(onDismiss)
    var shouldShowInfo by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Trigger the animation after a short delay to reveal the info card
    LaunchedEffect(Unit) {
        delay(INFO_APPEAR_DELAY_MS)
        shouldShowInfo = true
    }

    // Compute vertical offset for the image during animation
    val imageOffset = animatedOffsetY(
        targetValue = if (shouldShowInfo) -CONTENT_HEIGHT / 2 else 0f
    )

    // Compute vertical offset for the info card during animation
    val infoOffset = animatedOffsetY(
        targetValue = if (shouldShowInfo) IMAGE_HEIGHT - CONTENT_HEIGHT / 2 else INITIAL_CONTENT_OFFSET_Y
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = OVERLAY_ALPHA))
            .clickable {
                scope.launch {
                    // Start hide animation
                    shouldShowInfo = false
                    // Wait for animation to complete before dismissing
                    delay(INFO_DISMISS_DELAY_MS)
                    updatedOnDismiss()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth(IMAGE_CARD_WIDTH_PERCENT)) {
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
        animationSpec = tween(durationMillis = ANIMATION_DURATION_MS),
        label = "animated_offset_y"
    )
}

@Composable
private fun InfoCard(dog: Dog, offsetY: Dp) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(CONTENT_HEIGHT.dp)
            .offset(y = offsetY),
        shape = RoundedCornerShape(
            bottomStart = CARD_CORNER_RADIUS_DP.dp,
            bottomEnd = CARD_CORNER_RADIUS_DP.dp
        ),
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
            .height(IMAGE_HEIGHT.dp)
            .offset(y = offsetY),
        shape = RoundedCornerShape(
            topStart = CARD_CORNER_RADIUS_DP.dp,
            topEnd = CARD_CORNER_RADIUS_DP.dp
        ),
    ) {
        AsyncImage(
            model = dog.image,
            contentDescription = dog.dogName,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
