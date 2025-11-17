package com.immersionlog.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.immersionlog.app.ui.AppNavHost
import com.immersionlog.app.ui.theme.ImmersionLogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImmersionLogTheme {
                AppNavHost()
            }
        }
    }
}
