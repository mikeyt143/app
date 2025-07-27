package com.example.shiftracker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftracker.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.Duration

data class ShiftUiState(
    val shifts: List<Shift> = emptyList(),
    val totalEarnings: Double = 0.0
)

class ShiftViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = ShiftRepository(ShiftDatabase.getDatabase(app).shiftDao())

    private val _uiState = MutableStateFlow(ShiftUiState())
    val uiState: StateFlow<ShiftUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repo.allShifts.collect { shifts ->
                val total = shifts.sumOf { calcPay(it) }
                _uiState.value = ShiftUiState(shifts, total)
            }
        }
    }

    fun addShift(shift: Shift) = viewModelScope.launch { repo.insert(shift) }
    fun deleteShift(shift: Shift) = viewModelScope.launch { repo.delete(shift) }

    fun calcPay(shift: Shift): Double {
        val start = LocalTime.parse(shift.startTime)
        val end = LocalTime.parse(shift.endTime)
        val duration = Duration.between(start, end).toMinutes()
        val payableMinutes = if (shift.paidBreak) duration else duration - shift.breakMinutes
        val hours = payableMinutes / 60.0
        return (if (hours > 0) hours else 0.0) * shift.hourlyWage
    }
}