package com.makeevrserg.godotmobile.features.godot.plugin

import com.makeevrserg.godotmobile.features.godot.plugin.model.PluginIntent
import org.godotengine.godot.plugin.GodotPlugin

interface GodotEventConsumer {
    fun onIntent(intent: PluginIntent)
}

class GodotEventConsumerImpl(
    private val getPlugins: () -> Set<GodotPlugin>
) : GodotEventConsumer {
    override fun onIntent(intent: PluginIntent) {
        getPlugins.invoke()
            .filterIsInstance<IntentGodotPlugin>()
            .onEach { it.onIntent(intent) }
    }
}
