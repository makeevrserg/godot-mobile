package com.makeevrserg.godotmobile.features.godot.plugin

import com.makeevrserg.godotmobile.features.godot.plugin.model.PluginIntent
import com.makeevrserg.godotmobile.features.godot.plugin.model.PluginIntentEnum
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
            is PluginIntent.SetBackground -> PluginIntentEnum.SET_BACKGROUND
            is PluginIntent.SetBallScale -> PluginIntentEnum.SET_BALL_SCALE
            is PluginIntent.SetBallColor -> PluginIntentEnum.SET_BALL_COLOR
            is PluginIntent.SetBallSkew -> PluginIntentEnum.SET_BALL_SKEW
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

            is PluginIntent.SetBackground -> emitSignal(
                intent.toEnum().signalInfo.name,
                intent.color.red.times(255).toInt(),
                intent.color.green.times(255).toInt(),
                intent.color.blue.times(255).toInt(),
                intent.color.alpha.times(255).toInt()
            )

            is PluginIntent.SetBallScale -> emitSignal(
                intent.toEnum().signalInfo.name,
                intent.index,
                intent.scale
            )

            is PluginIntent.SetBallColor -> emitSignal(
                intent.toEnum().signalInfo.name,
                intent.index,
                intent.color.red.times(255).toInt(),
                intent.color.green.times(255).toInt(),
                intent.color.blue.times(255).toInt(),
                intent.color.alpha.times(255).toInt()
            )

            is PluginIntent.SetBallSkew -> emitSignal(
                intent.toEnum().signalInfo.name,
                intent.index,
                intent.skew,
            )
        }
    }
}
