package com.example.a7minutesworkout

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.DialogCompat
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.CustomDialogBinding
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding : ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null

    private var restProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts : TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //Keeping the screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        tts = TextToSpeech(this, this)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        binding?.toolbarExercise?.setNavigationOnClickListener{
            customDialogForBackButton()
        }


        setupRestView()
        setupExerciseStatusRecyclerView()

    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        val dialogBinding = CustomDialogBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        val window = customDialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.buttonYes.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.buttonNo.setOnClickListener{
            customDialog.dismiss()
        }

        customDialog.show()
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun setupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun setupRestView(){
        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
            binding?.exerciseImage?.visibility = View.GONE
            binding?.progressbar?.max = 10

            try {
                 val soundURI = Uri.parse(
                    "android.resource://com.example.a7minutesworkout/" + R.raw.press_start)
                player  = MediaPlayer.create(applicationContext, soundURI)
                player?.isLooping = false
                player?.start()
            }catch (e: Exception){
                e.printStackTrace()
            }


            if (currentExercisePosition < exerciseList!!.size -1){
                binding?.title?.text = "GET SOME REST"
                binding?.LlNextExercise?.visibility = View.VISIBLE
                binding?.nextExerciseText?.text = exerciseList!![currentExercisePosition +1].name
            }
        }
        setRestProgressBar()
    }

    private fun setRestProgressBar(){
        binding?.progressbar?.progress = restProgress

        restTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressbar?.progress = 10 - restProgress
                binding?.timer?.text = (10-restProgress).toString()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                if(currentExercisePosition < exerciseList!!.size - 1) {
                    currentExercisePosition++

                    exerciseList!![currentExercisePosition].isSelected = true
                    exerciseAdapter!!.notifyDataSetChanged()

                    setExercise()
                }
            }

        }.start()
    }

    private fun setExercise(){
        speakOut(exerciseList?.get(currentExercisePosition)?.name.toString())
        restProgress = 0
        binding?.exerciseImage?.visibility = View.VISIBLE
        binding?.LlNextExercise?.visibility = View.GONE
        binding?.exerciseImage?.setImageResource(exerciseList!![currentExercisePosition].image)
        binding?.title?.text = exerciseList?.get(currentExercisePosition)?.name
        binding?.progressbar?.max = 30
        binding?.progressbar?.progress = 300
        binding?.timer?.text = (30).toString()
        restTimer = object : CountDownTimer(30000, 1000){
            override fun onTick(millisUntiElFinished: Long) {
                restProgress++
                binding?.progressbar?.progress = 30 - restProgress
                binding?.timer?.text = (30 -restProgress).toString()
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                exerciseList!![currentExercisePosition].isSelected = false
                exerciseList!![currentExercisePosition].isCompleted = true
                exerciseAdapter!!.notifyDataSetChanged()

                if (currentExercisePosition < exerciseList!!.size -1){
                    setupRestView()
                }else{
                    congratsView()
                }
            }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun congratsView(){

        val intent = Intent(this@ExerciseActivity, CongratulationActivity::class.java)
        startActivity(intent)

        try {
            val soundURI = Uri.parse(
                "android.resource://com.example.a7minutesworkout/" + R.raw.press_start)
            player  = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The Language is not supported!")
            }else{
                Log.e("TTS", "Initialization Failed!")
            }
        }
    }


    private fun speakOut(text: String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH,null, "")
    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        if (tts != null){
            tts?.stop()
            tts?.shutdown()
        }

        if (player != null){
            player!!.stop()
        }

        binding = null

    }

}