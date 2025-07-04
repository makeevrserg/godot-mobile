package com.makeevrserg.godotmobile.features.godot.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.fragment.compose.AndroidFragment
import com.arkivanov.decompose.router.pages.navigate
import com.makeevrserg.godotmobile.features.godot.plugin.GodotEventConsumer
import io.github.xxfast.decompose.router.pages.RoutedContent
import io.github.xxfast.decompose.router.pages.Router
import io.github.xxfast.decompose.router.pages.pagesOf
import io.github.xxfast.decompose.router.pages.rememberRouter
import kotlinx.serialization.Serializable
import org.godotengine.godot.GodotFragment
import kotlin.random.Random

@Serializable
sealed interface GodotScreen {
    data object Default : GodotScreen
    data class Parametrized(
        val backgroundColor: Color = Color.Black,
        val ball1: BallParam = BallParam(),
        val ball2: BallParam = BallParam(),
        val ball3: BallParam = BallParam(),
        val ball4: BallParam = BallParam()
    ) : GodotScreen {
        data class BallParam(
            val scale: Float = Random.nextFloat(),
            val color: Color = Color(Random.nextInt()).copy(alpha = 1f),
            val skew: Float = Random.nextDouble(0.0, 0.1).toFloat()
        )
    }
}

@Composable
fun GodotScreenComposable(
    modifier: Modifier = Modifier,
    godotEventConsumer: GodotEventConsumer,
) {
    val router: Router<GodotScreen> = rememberRouter { pagesOf(GodotScreen.Default) }
    val onBack = {
        router.pop()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            AndroidFragment(
                modifier = modifier.size(400.dp),
                clazz = GodotFragment::class.java
            )
        }
    }
    RoutedContent(
        modifier = modifier,
        router = router,
        content = { screen ->
            when (screen) {
                GodotScreen.Default -> DefaultGodotComposable(
                    modifier = Modifier,
                    godotEventConsumer = godotEventConsumer,
                    onBack = onBack,
                    onPushParametrized = {
                        router.push(GodotScreen.Parametrized())
                    }
                )

                is GodotScreen.Parametrized -> ParamGodotComposable(
                    parametrized = screen,
                    modifier = Modifier,
                    onBack = onBack,
                    godotEventConsumer = godotEventConsumer,
                    onPushParametrized = {
                        router.push(GodotScreen.Parametrized())
                    }
                )
            }
        }
    )
}

private fun <T : Any> Router<T>.push(item: T) {
    navigate {
        it.copy(
            items = it.items + item,
            selectedIndex = it.items.lastIndex + 1
        )
    }
}

private fun <T : Any> Router<T>.pop() {
    navigate {
        if (it.items.size == 1) {
            it
        } else {
            it.copy(
                items = it.items - it.items.last(),
                selectedIndex = it.selectedIndex.minus(1).coerceAtLeast(0)
            )
        }
    }
}
