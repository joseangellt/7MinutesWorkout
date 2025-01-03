package com.example.a7minutesworkout

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        
        val jumpingJacks = ExerciseModel(
            1,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks
            )
        exerciseList.add(jumpingJacks)

        val abdominalCrunch = ExerciseModel(
            2,
            "Abdominal Crunch",
            R.drawable.ic_abdominal_crunch
        )
        exerciseList.add(abdominalCrunch)

        val highKneesRunningInPlace = ExerciseModel(
            3,
            "High Knees Running In Place",
            R.drawable.ic_high_knees_running_in_place
        )
        exerciseList.add(highKneesRunningInPlace)

        val lunge = ExerciseModel(
            4,
            "Lunge",
            R.drawable.ic_lunge
        )
        exerciseList.add(lunge)

        val plank = ExerciseModel(
            5,
            "Plank",
            R.drawable.ic_plank
        )
        exerciseList.add(plank)

        val pushUp = ExerciseModel(
            6,
            "Push Up",
            R.drawable.ic_push_up
        )
        exerciseList.add(pushUp)

        val pushUpAnRotation = ExerciseModel(
            7,
            "Push Up And Rotation",
            R.drawable.ic_push_up_and_rotation
        )
        exerciseList.add(pushUpAnRotation)

        val sidePlank = ExerciseModel(
            8,
            "Side Plank",
            R.drawable.ic_side_plank
        )
        exerciseList.add(sidePlank)

        val squat = ExerciseModel(
            9,
            "Squat",
            R.drawable.ic_squat
        )
        exerciseList.add(squat)

        val stepOnChair = ExerciseModel(
            10,
            "Step On Chair",
            R.drawable.ic_step_up_onto_chair
        )
        exerciseList.add(stepOnChair)

        val tricepsDipsOnChair = ExerciseModel(
            11,
            "Triceps Dips On Chair",
            R.drawable.ic_triceps_dip_on_chair
        )
        exerciseList.add(tricepsDipsOnChair)

        val wallSit = ExerciseModel(
            12,
            "Wall Sit",
            R.drawable.ic_wall_sit
        )
        exerciseList.add(wallSit)


        return exerciseList
    }


}