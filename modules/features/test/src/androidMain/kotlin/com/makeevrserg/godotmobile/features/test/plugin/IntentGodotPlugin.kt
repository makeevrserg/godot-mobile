package com.makeevrserg.godotmobile.features.test.plugin

import com.makeevrserg.godotmobile.features.test.plugin.model.PluginIntent
import com.makeevrserg.godotmobile.features.test.plugin.model.PluginIntentEnum
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin

class IntentGodotPlugin(godot: Godot) : GodotPlugin(godot) {

    override fun getPluginName() = "IntentGodotPlugin"

    override fun getPluginSignals() = PluginIntentEnum.entries
        .map(PluginIntentEnum::signalInfo)
        .toSet()

    private fun PluginIntent.toEnum(): PluginIntentEnum {
        return when (this) {
            is PluginIntent.ShowGltf -> PluginIntentEnum.SHOW_GLTF
        }
    }

    fun onIntent(intent: PluginIntent) {
        when (intent) {
            is PluginIntent.ShowGltf -> {
                emitSignal(
                    intent.toEnum().signalInfo.name,
                    intent.path
                )
            }
        }
    }
}
