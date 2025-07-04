package com.makeevrserg.godotmobile.features.godot.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.makeevrserg.godotmobile.features.godot.activity.util.setContent
import com.makeevrserg.godotmobile.features.godot.composable.GodotScreenComposable
import com.makeevrserg.godotmobile.features.godot.host.KGodotHost
import com.makeevrserg.godotmobile.features.godot.host.KGodotHostImpl
import com.makeevrserg.godotmobile.features.godot.plugin.GodotEventConsumerImpl
import com.makeevrserg.godotmobile.features.godot.plugin.IntentGodotPlugin
import io.github.xxfast.decompose.router.LocalRouterContext
import io.github.xxfast.decompose.router.RouterContext
import io.github.xxfast.decompose.router.defaultRouterContext
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
        val rootRouterContext: RouterContext = defaultRouterContext()
        setContent {
            CompositionLocalProvider(
                LocalRouterContext provides rootRouterContext,
                content = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                        content = {
                            GodotScreenComposable(
                                Modifier,
                                godotEventConsumer = godotEventConsumer,
                            )
                        }
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
