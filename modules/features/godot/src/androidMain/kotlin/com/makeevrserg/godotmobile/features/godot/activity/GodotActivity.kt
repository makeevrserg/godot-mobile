package com.makeevrserg.godotmobile.features.godot.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.makeevrserg.godotmobile.features.godot.composable.TestComposable
import com.makeevrserg.godotmobile.features.godot.composable.util.setContent
import com.makeevrserg.godotmobile.features.godot.host.KGodotHost
import com.makeevrserg.godotmobile.features.godot.host.KGodotHostImpl
import com.makeevrserg.godotmobile.features.godot.plugin.GodotEventConsumerImpl
import com.makeevrserg.godotmobile.features.godot.plugin.IntentGodotPlugin
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin

class GodotActivity : FragmentActivity(), KGodotHost {
    private val kGodotHost by lazy {
        KGodotHostImpl(
            this,
            createPlugins = { godot ->
                setOf(
                    IntentGodotPlugin(godot)
                )
            }
        )
    }
    private val godotEventConsumer by lazy {
        GodotEventConsumerImpl(getPlugins = { getHostPlugins(godot) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = {
                    TestComposable(
                        Modifier,
                        godotEventConsumer = godotEventConsumer
                    )
                }
            )
        }
    }

    override fun getActivity(): Activity {
        return kGodotHost.activity
    }

    override fun getGodot(): Godot {
        return kGodotHost.godot
    }

    override fun getHostPlugins(engine: Godot?): Set<GodotPlugin> {
        return kGodotHost.getHostPlugins(engine)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
