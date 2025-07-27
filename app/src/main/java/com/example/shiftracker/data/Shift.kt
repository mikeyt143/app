package com.example.shiftracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shift(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String, // yyyy-MM-dd
    val startTime: String, // HH:mm
    val endTime: String,   // HH:mm
    val breakMinutes: Int,
    val paidBreak: Boolean,
    val hourlyWage: Double,
    val currency: String,
    val notes: String?
)