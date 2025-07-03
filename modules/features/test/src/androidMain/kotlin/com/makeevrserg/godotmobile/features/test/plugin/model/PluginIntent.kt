package com.makeevrserg.godotmobile.features.test.plugin.model

import androidx.compose.ui.graphics.Color

sealed interface PluginIntent {
    data class ShowGltf(val path: String) : PluginIntent
    data class SetBackground(val color: Color) : PluginIntent
}
