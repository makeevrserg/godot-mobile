package com.makeevrserg.godotmobile

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import com.makeevrserg.godotmobile.features.test.TestComposable
import com.makeevrserg.godotmobile.features.test.host.KGodotHost
import com.makeevrserg.godotmobile.features.test.host.KGodotHostImpl
import com.makeevrserg.godotmobile.features.test.plugin.GodotEventConsumerImpl
import com.makeevrserg.godotmobile.features.test.plugin.IntentGodotPlugin
import com.makeevrserg.godotmobile.util.setContent
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin

internal class MainActivity : FragmentActivity(), KGodotHost {
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
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { false }

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
}
