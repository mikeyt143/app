package com.example.shiftracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shiftracker.ui.theme.ShiftTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShiftTrackerTheme {
                val vm: ShiftViewModel = viewModel(factory = ShiftViewModelFactory(application))
                MainScreen(vm)
            }
        }
    }
}