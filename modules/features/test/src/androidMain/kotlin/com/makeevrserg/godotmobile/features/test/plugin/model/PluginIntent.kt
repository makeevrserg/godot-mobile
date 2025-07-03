package com.makeevrserg.godotmobile.features.test.plugin.model

sealed interface PluginIntent {
    data class ShowGltf(val path: String) : PluginIntent
}
