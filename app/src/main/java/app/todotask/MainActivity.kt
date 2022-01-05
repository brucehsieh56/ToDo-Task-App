package app.todotask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.view.WindowCompat
import app.todotask.screen.todotaskscreen.components.ToDoTaskScreen
import app.todotask.ui.theme.AppTheme
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets

@ExperimentalMaterial3Api
@ExperimentalAnimatedInsets
@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Remove window decor
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    ToDoTaskScreen()
                }
            }
        }
    }
}