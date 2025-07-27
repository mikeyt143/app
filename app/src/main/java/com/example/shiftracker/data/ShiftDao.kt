package com.example.shiftracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftDao {
    @Query("SELECT * FROM Shift ORDER BY date DESC, startTime DESC")
    fun getAllShifts(): Flow<List<Shift>>

    @Insert
    suspend fun insert(shift: Shift): Long

    @Delete
    suspend fun delete(shift: Shift)
}