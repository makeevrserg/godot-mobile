package com.makeevrserg.godotmobile.features.godot.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeevrserg.godotmobile.features.godot.composable.component.Button
import com.makeevrserg.godotmobile.features.godot.composable.component.MyDialog
import com.makeevrserg.godotmobile.features.godot.composable.model.State
import com.makeevrserg.godotmobile.features.godot.plugin.GodotEventConsumer
import com.makeevrserg.godotmobile.features.godot.plugin.model.PluginIntent

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
    LaunchedEffect(parametrized) {
        godotEventConsumer.onIntent(PluginIntent.SetBackground(parametrized.backgroundColor))
        godotEventConsumer.onIntent(PluginIntent.SetBallColor(1, parametrized.ball1.color))
        godotEventConsumer.onIntent(PluginIntent.SetBallColor(2, parametrized.ball2.color))
        godotEventConsumer.onIntent(PluginIntent.SetBallColor(3, parametrized.ball3.color))
        godotEventConsumer.onIntent(PluginIntent.SetBallColor(4, parametrized.ball4.color))

        godotEventConsumer.onIntent(PluginIntent.SetBallScale(1, parametrized.ball1.scale))
        godotEventConsumer.onIntent(PluginIntent.SetBallScale(2, parametrized.ball2.scale))
        godotEventConsumer.onIntent(PluginIntent.SetBallScale(3, parametrized.ball3.scale))
        godotEventConsumer.onIntent(PluginIntent.SetBallScale(4, parametrized.ball4.scale))

        godotEventConsumer.onIntent(PluginIntent.SetBallSkew(1, parametrized.ball1.skew))
        godotEventConsumer.onIntent(PluginIntent.SetBallScale(2, parametrized.ball2.skew))
        godotEventConsumer.onIntent(PluginIntent.SetBallScale(3, parametrized.ball3.skew))
        godotEventConsumer.onIntent(PluginIntent.SetBallScale(4, parametrized.ball4.skew))
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
