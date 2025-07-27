package com.example.shiftracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Shift::class], version = 1)
abstract class ShiftDatabase : RoomDatabase() {
    abstract fun shiftDao(): ShiftDao

    companion object {
        @Volatile private var INSTANCE: ShiftDatabase? = null

        fun getDatabase(context: Context): ShiftDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ShiftDatabase::class.java,
                    "shift_db"
                ).build().also { INSTANCE = it }
            }
    }
}