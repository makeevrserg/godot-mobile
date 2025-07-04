package com.makeevrserg.godotmobile.features.test.plugin.model

import androidx.compose.ui.graphics.Color

sealed interface PluginIntent {
    data class ShowGltf(val path: String) : PluginIntent
    data class SetBackground(val color: Color) : PluginIntent
    data class SetBallScale(val index: Int, val scale: Float) : PluginIntent
    data class SetBallColor(val index: Int, val color: Color) : PluginIntent
    data class SetBallSkew(val index: Int, val skew: Float) : PluginIntent
}
