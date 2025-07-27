package com.example.shiftracker.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShiftViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShiftViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShiftViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}