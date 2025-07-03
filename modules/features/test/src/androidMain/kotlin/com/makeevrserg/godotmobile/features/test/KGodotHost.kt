package com.makeevrserg.godotmobile.features.test

import android.app.Activity
import org.godotengine.godot.Godot
import org.godotengine.godot.GodotHost
import java.lang.ref.WeakReference

object KGodotHost : GodotHost {
    private var currentActivity = WeakReference<Activity>(null)
    val requireActivity: Activity
        get() = currentActivity.get() ?: error("Activity is destroyed")

    fun onActivityReady(activity: Activity) {
        currentActivity = WeakReference(activity)
    }

    fun onActivityDestroyed() {
        currentActivity = WeakReference(null)
    }

    private val godotInstance by lazy {
        Godot(requireActivity.applicationContext)
    }

    override fun getActivity(): Activity? = currentActivity.get()

    override fun getGodot(): Godot = godotInstance
}
