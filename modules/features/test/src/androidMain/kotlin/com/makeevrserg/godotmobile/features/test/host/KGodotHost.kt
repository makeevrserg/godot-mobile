package com.makeevrserg.godotmobile.features.test.host

import android.app.Activity
import org.godotengine.godot.Godot
import org.godotengine.godot.GodotHost
import org.godotengine.godot.plugin.GodotPlugin

interface KGodotHost : GodotHost {
    override fun getActivity(): Activity
    override fun getGodot(): Godot
    override fun getHostPlugins(engine: Godot?): Set<GodotPlugin>
}

class KGodotHostImpl(
    activity: Activity,
    createPlugins: (Godot) -> Set<GodotPlugin>
) : KGodotHost {
    private val godotInstance by lazy {
        Godot(activity.applicationContext)
    }
    private val plugins by lazy {
        createPlugins.invoke(godotInstance)
    }

    override fun getActivity(): Activity = activity

    override fun getGodot(): Godot = godotInstance

    override fun getHostPlugins(engine: Godot?): Set<GodotPlugin> {
        return plugins
    }
}
