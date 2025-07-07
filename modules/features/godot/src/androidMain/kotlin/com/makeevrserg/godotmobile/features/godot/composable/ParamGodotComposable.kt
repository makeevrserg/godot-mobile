package com.makeevrserg.godotmobile.features.godot.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeevrserg.godotmobile.features.godot.composable.GodotScreen.Parametrized.BallParam
import com.makeevrserg.godotmobile.features.godot.composable.component.Button
import com.makeevrserg.godotmobile.features.godot.composable.component.MyDialog
import com.makeevrserg.godotmobile.features.godot.composable.model.State
import com.makeevrserg.godotmobile.features.godot.plugin.GodotEventConsumer
import com.makeevrserg.godotmobile.features.godot.plugin.model.PluginIntent

@Composable
fun BallChangeParamsDialog(
    state: BallParam,
    onStateChange: (BallParam) -> Unit,
    onDismiss: () -> Unit
) {
    MyDialog(
        title = "Change Ball Param",
        onDismiss = { onDismiss.invoke() },
        content = {
            Column {
                Text("Ball Color")
                Slider(
                    value = state.color.red,
                    onValueChange = { red ->
                        onStateChange.invoke(state.copy(color = state.color.copy(red = red)))
                    }
                )
                Slider(
                    value = state.color.green,
                    onValueChange = { green ->
                        onStateChange.invoke(state.copy(color = state.color.copy(green = green)))
                    }
                )
                Slider(
                    value = state.color.blue,
                    onValueChange = { blue ->
                        onStateChange.invoke(state.copy(color = state.color.copy(blue = blue)))
                    }
                )
                Text("Ball Scale")
                Slider(
                    value = state.scale,
                    valueRange = 0f..2f,
                    onValueChange = { scale ->
                        onStateChange.invoke(state.copy(scale = scale))
                    }
                )
                Text("Ball Skew")
                Slider(
                    value = state.skew,
                    valueRange = -1f..1f,
                    onValueChange = { skew ->
                        onStateChange.invoke(state.copy(skew = skew))
                    }
                )
            }
        }
    )
}

@Suppress("LongMethod")
@Composable
fun ParamGodotComposable(
    modifier: Modifier = Modifier,
    parametrized: GodotScreen.Parametrized,
    godotEventConsumer: GodotEventConsumer,
    onPushParametrized: () -> Unit,
    onBack: () -> Unit
) {
    val mutableState = remember { mutableStateOf(State()) }
    val state by mutableState

    val ball1State = remember(parametrized) {
        mutableStateOf(parametrized.ball1)
    }
    val ball2State = remember(parametrized) {
        mutableStateOf(parametrized.ball2)
    }
    val ball3State = remember(parametrized) {
        mutableStateOf(parametrized.ball3)
    }
    val ball4State = remember(parametrized) {
        mutableStateOf(parametrized.ball4)
    }
    LaunchedEffect(
        state.background,
        ball1State.value,
        ball2State.value,
        ball3State.value,
        ball4State.value
    ) {
        godotEventConsumer.onIntent(PluginIntent.SetBackground(state.background))

        godotEventConsumer.onIntent(PluginIntent.SetBallColor(1, ball1State.value.color))
        godotEventConsumer.onIntent(PluginIntent.SetBallColor(2, ball2State.value.color))
        godotEventConsumer.onIntent(PluginIntent.SetBallColor(3, ball3State.value.color))
        godotEventConsumer.onIntent(PluginIntent.SetBallColor(4, ball4State.value.color))

        godotEventConsumer.onIntent(PluginIntent.SetBallScale(1, ball1State.value.scale))
        godotEventConsumer.onIntent(PluginIntent.SetBallScale(2, ball2State.value.scale))
        godotEventConsumer.onIntent(PluginIntent.SetBallScale(3, ball3State.value.scale))
        godotEventConsumer.onIntent(PluginIntent.SetBallScale(4, ball4State.value.scale))

        godotEventConsumer.onIntent(PluginIntent.SetBallSkew(1, ball1State.value.skew))
        godotEventConsumer.onIntent(PluginIntent.SetBallSkew(2, ball2State.value.skew))
        godotEventConsumer.onIntent(PluginIntent.SetBallSkew(3, ball3State.value.skew))
        godotEventConsumer.onIntent(PluginIntent.SetBallSkew(4, ball4State.value.skew))
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(contentAlignment = Alignment.Center, modifier = modifier.size(400.dp)) {
        }

        FlowRow(modifier = Modifier.align(Alignment.BottomStart)) {
            Button(
                text = "Background",
                onClick = {
                    mutableState.value = state.copy(isBackgroundDialogVisible = true)
                }
            )
            Button(
                text = "Push Parametrized",
                onClick = {
                    onPushParametrized.invoke()
                }
            )
            Button(
                text = "Back",
                onClick = {
                    onBack.invoke()
                }
            )
            listOf(1, 2, 3, 4).onEach { index ->
                Button(
                    text = "Ball $index config",
                    onClick = {
                        mutableState.value = state.copy(ballIndexSetupVisible = index)
                    }
                )
            }
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
                                mutableState.value =
                                    state.copy(background = state.background.copy(red = red))
                            }
                        )
                        Slider(
                            value = state.background.green,
                            onValueChange = { green ->
                                mutableState.value =
                                    state.copy(background = state.background.copy(green = green))
                            }
                        )
                        Slider(
                            value = state.background.blue,
                            onValueChange = { blue ->
                                mutableState.value =
                                    state.copy(background = state.background.copy(blue = blue))
                            }
                        )
                    }
                }
            )
        }
        state.ballIndexSetupVisible?.let {
            val ballState = when (it) {
                1 -> ball1State
                2 -> ball2State
                3 -> ball3State
                4 -> ball4State
                else -> {
                    mutableState.value = state.copy(ballIndexSetupVisible = null)
                    return@let
                }
            }
            BallChangeParamsDialog(
                state = ballState.value,
                onStateChange = { ballState.value = it },
                onDismiss = { mutableState.value = state.copy(ballIndexSetupVisible = null) }
            )
        }
    }
}
