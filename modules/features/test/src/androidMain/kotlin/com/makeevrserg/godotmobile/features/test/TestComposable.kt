package com.makeevrserg.godotmobile.features.test

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.compose.AndroidFragment
import com.makeevrserg.godotmobile.features.test.plugin.GodotEventConsumer
import com.makeevrserg.godotmobile.features.test.plugin.model.PluginIntent
import org.godotengine.godot.GodotFragment

private data class State(
    val background: Color = Color.Black,
    val isBackgroundDialogVisible: Boolean = false
)

private val mutableState = mutableStateOf(State())

@Composable
private fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .background(Color.Red)
            .padding(14.dp)
            .clickable {
                onClick.invoke()
            },
        text = text,
    )
}

@Composable
fun MyDialog(
    title: String,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colors.primaryVariant)
                        .padding(12.dp)
                ) {
                    Text(text = title)
                    Spacer(Modifier.height(12.dp))
                    content.invoke()
                }
            }
        }
    )
}

@Composable
fun TestComposable(
    modifier: Modifier = Modifier,
    godotEventConsumer: GodotEventConsumer
) {
    val state by mutableState
    LaunchedEffect(state.background) {
        godotEventConsumer.onIntent(PluginIntent.SetBackground(state.background))
    }
    // 1
    val scaleAnimation1 = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        )
    )
    LaunchedEffect(scaleAnimation1.value) {
        godotEventConsumer.onIntent(PluginIntent.SetBallScale(1, scaleAnimation1.value))
    }
    // 2
    val scaleAnimation2 = rememberInfiniteTransition().animateFloat(
        initialValue = 0.3f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        )
    )
    LaunchedEffect(scaleAnimation2.value) {
        godotEventConsumer.onIntent(PluginIntent.SetBallScale(2, scaleAnimation2.value))
    }
    // 3
    val scaleAnimation3 = rememberInfiniteTransition().animateFloat(
        initialValue = 0.2f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        )
    )
    val colorAnimation3 = rememberInfiniteTransition().animateColor(
        initialValue = Color.Green,
        targetValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        )
    )
    LaunchedEffect(scaleAnimation3.value, colorAnimation3.value) {
        godotEventConsumer.onIntent(PluginIntent.SetBallScale(3, scaleAnimation2.value))
        godotEventConsumer.onIntent(PluginIntent.SetBallColor(3, colorAnimation3.value))
    }
    // 4
    val skewAnimation = rememberInfiniteTransition().animateFloat(
        initialValue = 0.0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        )
    )
    val colorAnimation4 = rememberInfiniteTransition().animateColor(
        initialValue = Color.White,
        targetValue = Color.Cyan,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        )
    )
    val scaleAnimation4 = rememberInfiniteTransition().animateFloat(
        initialValue = 0.2f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        )
    )
    LaunchedEffect(skewAnimation.value, colorAnimation4.value, scaleAnimation4.value) {
        godotEventConsumer.onIntent(PluginIntent.SetBallSkew(4, skewAnimation.value))
        godotEventConsumer.onIntent(PluginIntent.SetBallScale(4, scaleAnimation4.value))
        godotEventConsumer.onIntent(PluginIntent.SetBallColor(4, colorAnimation4.value))
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            AndroidFragment(
                modifier = modifier.size(400.dp),
                clazz = GodotFragment::class.java
            )
            Text(
                text = "This is Compose text above!",
                modifier = Modifier.background(colorAnimation3.value)
            )
        }

        FlowRow(modifier = Modifier.align(Alignment.BottomStart)) {
            Button(
                text = "Background",
                onClick = {
                    mutableState.value = state.copy(isBackgroundDialogVisible = true)
                }
            )
        }
        if (state.isBackgroundDialogVisible) {
            MyDialog(
                title = "Change background",
                onDismiss = { mutableState.value = state.copy(isBackgroundDialogVisible = false) },
                content = {
                    Column {
                        Slider(
                            value = state.background.red,
                            onValueChange = { red ->
                                mutableState.value = state.copy(background = state.background.copy(red = red))
                            }
                        )
                        Slider(
                            value = state.background.green,
                            onValueChange = { green ->
                                mutableState.value = state.copy(background = state.background.copy(green = green))
                            }
                        )
                        Slider(
                            value = state.background.blue,
                            onValueChange = { blue ->
                                mutableState.value = state.copy(background = state.background.copy(blue = blue))
                            }
                        )
                    }
                }
            )
        }
    }
}
