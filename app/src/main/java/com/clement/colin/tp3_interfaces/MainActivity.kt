package com.clement.colin.tp3_interfaces

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView1: TextView
    private lateinit var resultTextView2: TextView
    private lateinit var messageTextView: TextView
    private lateinit var targetNumberSlider: Slider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        resultTextView1 = findViewById(R.id.resultTextView1)
        resultTextView2 = findViewById(R.id.resultTextView2)
        messageTextView = findViewById(R.id.messageTextView)
        targetNumberSlider = findViewById(R.id.targetNumberSlider)

        // Set up automatic rolling when slider value changes
        setupAutoRoll()
    }

    private fun setupAutoRoll() {
        targetNumberSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                rollDice(value.toInt())
            }
        }
    }

    private fun animateWinningDice() {
        val bounceDistance = -50f  // bounce up distance in pixels

        val animator1 = ObjectAnimator.ofFloat(resultTextView1, "translationY", 0f, bounceDistance, 0f)
        val animator2 = ObjectAnimator.ofFloat(resultTextView2, "translationY", 0f, bounceDistance, 0f)

        animator1.apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        animator2.apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun rollDice(targetNumber: Int) {
        // Create new Dice object with 6 sides and roll both dice
        val dice = Dice(6)
        val diceRoll1 = dice.roll()
        val diceRoll2 = dice.roll()
        val sum = diceRoll1 + diceRoll2

        // Update the screen with both dice rolls
        resultTextView1.text = diceRoll1.toString()
        resultTextView2.text = diceRoll2.toString()

        // Check if sum equals target number and display appropriate message
        if (sum == targetNumber) {
            messageTextView.text = "Félicitations! La somme ($sum) correspond à votre nombre!"
            animateWinningDice()
        } else {
            messageTextView.text = "La somme est $sum. Essayez encore!"
        }
    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}