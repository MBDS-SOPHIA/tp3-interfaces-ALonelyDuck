package com.clement.colin.tp3_interfaces

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity allows the user to set a target sum and roll two dice.
 * The user wins if the sum of dice equals their target number.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView1: TextView
    private lateinit var resultTextView2: TextView
    private lateinit var messageTextView: TextView
    private lateinit var targetNumberInput: EditText
    private lateinit var rollButton: Button

    /**
     * This method is called when the Activity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        resultTextView1 = findViewById(R.id.resultTextView1)
        resultTextView2 = findViewById(R.id.resultTextView2)
        messageTextView = findViewById(R.id.messageTextView)
        targetNumberInput = findViewById(R.id.targetNumberInput)
        rollButton = findViewById(R.id.button)

        // Set up input validation
        setupTargetNumberValidation()

        // Set a click listener on the button to roll both dice when the user taps the button
        rollButton.setOnClickListener { rollDice() }
    }

    /**
     * Set up validation for the target number input field.
     */
    private fun setupTargetNumberValidation() {
        targetNumberInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val targetNumber = s.toString().toIntOrNull()
                rollButton.isEnabled = when {
                    targetNumber == null -> false
                    targetNumber < 2 || targetNumber > 12 -> false
                    else -> true
                }
            }
        })
    }

    /**
     * Roll both dice and update the screen with the results.
     * Check if the sum equals the target number.
     */
    private fun rollDice() {
        // Create new Dice object with 6 sides and roll both dice
        val dice = Dice(6)
        val diceRoll1 = dice.roll()
        val diceRoll2 = dice.roll()
        val sum = diceRoll1 + diceRoll2

        // Update the screen with both dice rolls
        resultTextView1.text = diceRoll1.toString()
        resultTextView2.text = diceRoll2.toString()

        // Get target number from input
        val targetNumber = targetNumberInput.text.toString().toInt()

        // Check if sum equals target number and display appropriate message
        if (sum == targetNumber) {
            messageTextView.text = "Félicitations! La somme ($sum) correspond à votre nombre!"
        } else {
            messageTextView.text = "La somme est $sum. Essayez encore!"
        }
    }
}

/**
 * Dice with a fixed number of sides.
 */
class Dice(private val numSides: Int) {
    /**
     * Do a random dice roll and return the result.
     */
    fun roll(): Int {
        return (1..numSides).random()
    }
}