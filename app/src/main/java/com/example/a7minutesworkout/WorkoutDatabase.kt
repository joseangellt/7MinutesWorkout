package com.example.a7minutesworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database( entities = [WorkoutEntity::class], version = 1)
abstract class WorkoutDatabase: RoomDatabase() {

    abstract fun workoutDao() : WorkoutDao

    companion object{

        @Volatile
        private var INSTANCE: WorkoutDatabase? = null

        fun getInstance(context: Context):WorkoutDatabase{
            synchronized(this){
              var instance = INSTANCE

              if(instance == null){
                  instance = Room.databaseBuilder(
                      context.applicationContext,
                      WorkoutDatabase::class.java,
                      "workout-database"
                  ).fallbackToDestructiveMigration()
                      .build()

                  INSTANCE = instance
              }
              return instance
            }
        }
    }
}