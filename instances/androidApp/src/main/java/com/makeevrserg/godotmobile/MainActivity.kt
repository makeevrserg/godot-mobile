package com.makeevrserg.godotmobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.makeevrserg.godotmobile.features.godot.activity.GodotActivity
import com.makeevrserg.godotmobile.features.lottie.activity.LottieActivity
import com.makeevrserg.godotmobile.features.unity.activity.UnityActivity

internal class MainActivity : ComponentActivity() {

    @Composable
    private fun Button(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Text(
            modifier = modifier
                .background(Color.Red)
                .padding(14.dp)
                .clickable {
                    onClick.invoke()
                },
            text = text,
        )
    }

    private fun <T : Activity> launchActivity(clazz: Class<T>) {
        val intent = Intent(this, clazz)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { false }

        super.onCreate(savedInstanceState)

        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(text = "GodotSample", onClick = { launchActivity(GodotActivity::class.java) })
                Button(text = "UnitySample", onClick = { launchActivity(UnityActivity::class.java) })
                Button(text = "LottieSample", onClick = { launchActivity(LottieActivity::class.java) })
            }
        }
    }
}
