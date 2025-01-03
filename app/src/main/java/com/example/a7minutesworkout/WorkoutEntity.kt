package com.example.a7minutesworkout


import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "workout-table")
data class WorkoutEntity (
    @PrimaryKey
    val date: String
)