package com.makeevrserg.godotmobile.features.test

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.fragment.compose.AndroidFragment
import com.makeevrserg.godotmobile.features.test.plugin.GodotEventConsumer
import com.makeevrserg.godotmobile.features.test.plugin.model.PluginIntent
import org.godotengine.godot.GodotFragment

@Composable
fun TestComposable(
    modifier: Modifier = Modifier,
    godotEventConsumer: GodotEventConsumer
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AndroidFragment(
            modifier = modifier.size(400.dp),
            clazz = GodotFragment::class.java
        )
        Text(
            modifier = Modifier.align(Alignment.BottomCenter)
                .background(Color.Red)
                .padding(14.dp)
                .clickable {
                    godotEventConsumer.onIntent(PluginIntent.ShowGltf("res://gltfs/food_kit/cheese.glb"))
                },
            text = "Next",
        )
    }
}
