package com.example.shiftracker.data

import kotlinx.coroutines.flow.Flow

class ShiftRepository(private val dao: ShiftDao) {
    val allShifts: Flow<List<Shift>> = dao.getAllShifts()
    suspend fun insert(shift: Shift) = dao.insert(shift)
    suspend fun delete(shift: Shift) = dao.delete(shift)
}