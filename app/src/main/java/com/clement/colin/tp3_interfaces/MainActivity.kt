package com.clement.colin.tp3_interfaces

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {

    private lateinit var diceImage1: ImageView
    private lateinit var diceImage2: ImageView
    private lateinit var messageTextView: TextView
    private lateinit var targetNumberSlider: Slider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diceImage1 = findViewById(R.id.diceImage1)
        diceImage2 = findViewById(R.id.diceImage2)
        messageTextView = findViewById(R.id.messageTextView)
        targetNumberSlider = findViewById(R.id.targetNumberSlider)

        setupAutoRoll()
    }

    private fun setupAutoRoll() {
        targetNumberSlider.addOnChangeListener { slider, value, fromUser ->
            slider.contentDescription = "Nombre sélectionné : ${value.toInt()}"
            if (fromUser) {
                rollDice(value.toInt())
            }
        }
    }

    private fun getDrawableResource(number: Int): Int {
        return when (number) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }

    private fun animateWinningDice() {
        // Créer les animations de rebond
        val bounceAnimation1 = createBounceAnimation(diceImage1)
        val bounceAnimation2 = createBounceAnimation(diceImage2)

        // Créer l'animation de rotation
        val rotateAnimation1 = createRotateAnimation(diceImage1)
        val rotateAnimation2 = createRotateAnimation(diceImage2)

        // Jouer les animations ensemble
        AnimatorSet().apply {
            playTogether(bounceAnimation1, bounceAnimation2, rotateAnimation1, rotateAnimation2)
            start()
        }
    }

    private fun createBounceAnimation(view: ImageView): AnimatorSet {
        val bounceUp = ObjectAnimator.ofFloat(view, "translationY", 0f, -100f)
        val bounceDown = ObjectAnimator.ofFloat(view, "translationY", -100f, 0f)

        return AnimatorSet().apply {
            playSequentially(bounceUp, bounceDown)
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
        }
    }

    private fun createRotateAnimation(view: ImageView): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, "rotation", 0f, 360f).apply {
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
        }
    }

    private fun rollDice(targetNumber: Int) {
        val dice = Dice(6)
        val diceRoll1 = dice.roll()
        val diceRoll2 = dice.roll()
        val sum = diceRoll1 + diceRoll2

        // Mettre à jour les images des dés
        diceImage1.setImageResource(getDrawableResource(diceRoll1))
        diceImage2.setImageResource(getDrawableResource(diceRoll2))

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