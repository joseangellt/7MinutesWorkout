package com.example.a7minutesworkout

import android.app.Person
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.emptyFlow
import java.util.concurrent.Flow

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insert(workoutEntity: WorkoutEntity)

    @Delete
    suspend fun delete(workoutEntity: WorkoutEntity)

    @Query ("SELECT * FROM `workout-table`")
      fun fetchAllDates(): kotlinx.coroutines.flow.Flow<List<WorkoutEntity>>

}