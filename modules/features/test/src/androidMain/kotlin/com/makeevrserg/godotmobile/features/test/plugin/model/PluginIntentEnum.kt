package com.makeevrserg.godotmobile.features.test.plugin.model

import org.godotengine.godot.plugin.SignalInfo

internal enum class PluginIntentEnum(val signalInfo: SignalInfo) {
    SHOW_GLTF(signalInfo = SignalInfo("show_gltf", String::class.java))
}
