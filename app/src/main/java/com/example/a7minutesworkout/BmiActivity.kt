package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

class BmiActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmi)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI !"
        }
        binding?.toolbarBmi?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.btnCalculate?.setOnClickListener{
            if (validateMetricUnits()){
                val heightValue : Float = binding?.heightEt?.text.toString().toFloat() / 100

                val weightValue : Float = binding?.weightEt?.text.toString().toFloat()

                val bmi = weightValue / heightValue.pow(2)
                displayBMIResult(bmi)
            }else if(validateUSUnits()) {
                //Calculating height in Inches
                val heightValue : Float =
                    ((binding?.heightFeetEt?.text.toString().toFloat() * 12)
                    + (binding?.heightInchEt?.text.toString().toFloat()))

                //getting the weight data and transforming to Float
                val weightValue : Float = binding?.weightInLbEt?.text.toString().toFloat()

                //Calculating bmi
                val bmi = (weightValue/heightValue.pow(2)) * (703)

                //Sending bmi result to display function
                displayBMIResult(bmi)
            }else{
                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }

        }

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if(checkedId == binding?.rbUsUnits?.id){
              makeVisibleUsUnitsView()
            }else{
                makeVisibleMetricUnitsView()
            }
        }

    }

    private fun makeVisibleUsUnitsView() {
        // Making the Us Units Visible
        binding?.heightFeetInputTi?.visibility = View.VISIBLE
        binding?.heightFeetEt?.visibility = View.VISIBLE
        binding?.heightInchInputTi?.visibility = View.VISIBLE
        binding?.heightInchEt?.visibility = View.VISIBLE
        binding?.weightInLbTi?.visibility = View.VISIBLE
        binding?.weightInLbEt?.visibility = View.VISIBLE



        // Making the  Metric Units Invisible
        binding?.heightInputTi?.visibility = View.INVISIBLE
        binding?.heightEt?.visibility = View.INVISIBLE
        binding?.weightTi?.visibility = View.INVISIBLE
        binding?.weightEt?.visibility = View.INVISIBLE
    }

    private fun makeVisibleMetricUnitsView(){
        //Making the  US Units Invisible
        binding?.heightFeetInputTi?.visibility = View.GONE
        binding?.heightFeetEt?.visibility = View.GONE
        binding?.heightInchInputTi?.visibility = View.GONE
        binding?.heightInchEt?.visibility = View.GONE
        binding?.weightInLbTi?.visibility = View.GONE
        binding?.weightInLbEt?.visibility = View.GONE

        //Making the  Metric Units Visible
        binding?.heightInputTi?.visibility = View.VISIBLE
        binding?.heightEt?.visibility = View.VISIBLE
        binding?.weightTi?.visibility = View.VISIBLE
        binding?.weightEt?.visibility = View.VISIBLE
    }

    private fun displayBMIResult (bmi : Float){

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! you really need to take better care of yourself! Eat more!"
        }else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! you really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "Underweight"
            bmiDescription = "Oops! you really need to take better care of yourself! Eat more!"
        }else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in good shape!"
        }else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Overweight"
            bmiDescription = "Oops! you really need to take better care of yourself! Workout maybe!"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! you really need to take better care of yourself! Workout maybe!"
        }else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0){
        bmiLabel = "Obese Class || (Severely obese)"
        bmiDescription = "You are in a very dangerous condition! Act now!"
    }else{
        bmiLabel = "Obese Class ||| (Very Severely obese)"
        bmiDescription = "You are in a very dangerous condition! Act now!"
    }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()

        binding?.LlBmiCalculation?.visibility = View.VISIBLE
        binding?.bmiResult?.text = bmiValue
        binding?.bmiConclusion?.text = bmiLabel
        binding?.bmiDescription?.text = bmiDescription
    }

    private fun validateMetricUnits() : Boolean{
        var isValid = true
        if(binding?.weightEt?.text.toString().isEmpty()){
            isValid = false
        }else  if (binding?.heightEt?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun validateUSUnits() : Boolean{
        var isValid = true
        if(binding?.weightInLbEt?.text.toString().isEmpty()){
            isValid = false
        }else  if (binding?.heightFeetEt?.text.toString().isEmpty()
            || binding?.heightInchEt?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

}