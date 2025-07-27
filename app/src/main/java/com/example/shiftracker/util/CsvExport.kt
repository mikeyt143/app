package com.example.shiftracker.util

import android.content.Context
import com.example.shiftracker.data.Shift
import java.io.File
import java.io.FileWriter

object CsvExport {
    fun export(context: Context, shifts: List<Shift>): File {
        val file = File(context.getExternalFilesDir(null), "shifts.csv")
        FileWriter(file).use { out ->
            out.append("Date,Start,End,Break,Paid?,Wage,Currency,Notes,Duration,Total Pay\n")
            for (s in shifts) {
                // TODO: calculate duration and totalPay
                out.append("${s.date},${s.startTime},${s.endTime},${s.breakMinutes},${s.paidBreak},${s.hourlyWage},${s.currency},\"${s.notes ?: ""}\",,\n")
            }
        }
        return file
    }
}