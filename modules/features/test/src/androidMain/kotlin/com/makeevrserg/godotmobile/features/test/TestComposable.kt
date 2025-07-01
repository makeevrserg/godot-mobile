package com.makeevrserg.godotmobile.features.test

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.compose.AndroidFragment
import org.godotengine.godot.GodotFragment

@Composable
fun TestComposable(modifier: Modifier = Modifier) {
    AndroidFragment(
        modifier = modifier,
        clazz = GodotFragment::class.java
    )
}
