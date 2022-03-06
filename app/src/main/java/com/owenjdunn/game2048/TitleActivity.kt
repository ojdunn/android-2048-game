package com.owenjdunn.game2048

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class TitleActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.title_screen)

        val startButton   = findViewById<Button>(R.id.startButton)
        val optionsButton = findViewById<Button>(R.id.optionsButton)
        val signInButton = findViewById<Button>(R.id.signInButton)
        val registerButton = findViewById<Button>(R.id.registerButton)


        // widget animation from xml file
        val shake = AnimationUtils.loadAnimation(this, R.anim.shake)

        // Set onClick(v: View?) function for button view generated click events.
        // This works because OnClickListener interface only has one method.
        // _ represents unused parameter by lambda function
        // launch a standard game on new activity
        startButton.setOnClickListener { _ ->
//            Snackbar.make(startButton /*any widget to find root ViewGroup*/, "START!!!", Snackbar.LENGTH_SHORT).show()
            startButton.startAnimation(shake)
        }
        //
        optionsButton.setOnClickListener { _ ->
            Snackbar.make(optionsButton, "No Options available yet", Snackbar.LENGTH_SHORT).show()
        }
        //
        signInButton.setOnClickListener { _ ->
            Snackbar.make(signInButton, "Local Data Only Now", Snackbar.LENGTH_SHORT).show()
        }
        //
        registerButton.setOnClickListener { _ ->
            Snackbar.make(registerButton, "Register not available yet", Snackbar.LENGTH_SHORT).show()
        }
    }
}