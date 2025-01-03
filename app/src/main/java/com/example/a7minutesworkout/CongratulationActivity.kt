package com.example.a7minutesworkout

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityCongratulationsBinding
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class CongratulationActivity: AppCompatActivity() {

    private var binding: ActivityCongratulationsBinding? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCongratulationsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val workoutDao = (application as WorkoutApp).db.workoutDao()

        binding?.buttonFinish?.setOnClickListener{
            val intent = Intent(this@CongratulationActivity, MainActivity::class.java)
            startActivity(intent)
            addRecord(workoutDao)
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addRecord(workoutDao: WorkoutDao){

        val actualDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val finalDate = actualDate.format(formatter)

        lifecycleScope.launch {
            workoutDao.insert(WorkoutEntity(finalDate))
            Toast.makeText(applicationContext, "Record Saved", Toast.LENGTH_LONG).show()
        }
    }

}