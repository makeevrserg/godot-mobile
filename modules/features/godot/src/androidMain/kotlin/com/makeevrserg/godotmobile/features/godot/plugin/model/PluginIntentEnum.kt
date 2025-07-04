package com.makeevrserg.godotmobile.features.godot.plugin.model

import org.godotengine.godot.plugin.SignalInfo

internal enum class PluginIntentEnum(val signalInfo: SignalInfo) {
    SHOW_GLTF(signalInfo = SignalInfo("show_gltf", String::class.java)),
    SET_BACKGROUND(
        signalInfo = SignalInfo(
            "set_background",
            Int::class.javaObjectType,
            Int::class.javaObjectType,
            Int::class.javaObjectType,
            Int::class.javaObjectType
        )
    ),
    SET_BALL_SCALE(
        signalInfo = SignalInfo(
            "set_ball_scale",
            Int::class.javaObjectType,
            Float::class.javaObjectType,
        )
    ),
    SET_BALL_COLOR(
        signalInfo = SignalInfo(
            "set_ball_color",
            Int::class.javaObjectType,
            Int::class.javaObjectType,
            Int::class.javaObjectType,
            Int::class.javaObjectType,
            Int::class.javaObjectType
        )
    ),
    SET_BALL_SKEW(
        signalInfo = SignalInfo(
            "set_ball_skew",
            Int::class.javaObjectType,
            Float::class.javaObjectType,
        )
    ),
}
