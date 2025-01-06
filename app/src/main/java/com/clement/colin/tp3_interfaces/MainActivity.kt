package com.clement.colin.tp3_interfaces

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity allows the user to roll two dice and view the result
 * on the screen. If both dice show the same number, the user wins.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView1: TextView
    private lateinit var resultTextView2: TextView
    private lateinit var messageTextView: TextView

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

        // Find the Button in the layout
        val rollButton: Button = findViewById(R.id.button)

        // Set a click listener on the button to roll both dice when the user taps the button
        rollButton.setOnClickListener { rollDice() }
    }

    /**
     * Roll both dice and update the screen with the results.
     * Check if the user won (same number on both dice).
     */
    private fun rollDice() {
        // Create new Dice object with 6 sides and roll both dice
        val dice = Dice(6)
        val diceRoll1 = dice.roll()
        val diceRoll2 = dice.roll()

        // Update the screen with both dice rolls
        resultTextView1.text = diceRoll1.toString()
        resultTextView2.text = diceRoll2.toString()

        // Check if user won and display appropriate message
        if (diceRoll1 == diceRoll2) {
            messageTextView.text = "Félicitations! Vous avez gagné!"
        } else {
            messageTextView.text = "Essayez encore!"
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