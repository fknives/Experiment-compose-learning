package org.fknives.android.compose.learning

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.fknives.android.compose.learning.navigation.Navigation
import org.fknives.android.compose.learning.theme.AppTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Navigation { onNavigationClick ->
                    MainContent(onNavigationClick)
                }
            }
        }
    }
}