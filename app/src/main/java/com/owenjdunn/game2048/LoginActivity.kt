package com.owenjdunn.game2048

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class LoginActivity: AppCompatActivity() {
    /**
     * Enable menu to appear on the app bar.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true     // menu should be visible if true
    }

    /**
     * Allow options menu selections.
     */
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val b: Boolean = // b assigned last expression reached of when statement
//            when (item.itemId) {
//                R.id.action_options -> {    // goto options screen
//                    val v = findViewById<Button>(R.id.floatingActionButtonStart)    // created just to use snackbar
//                    Snackbar.make(v, "No Options available yet", Snackbar.LENGTH_SHORT).show()  // remove later
//                    true
//                }
//                R.id.action_logout -> {
//                    finish()    // terminate activity
//                    true
//                }
//                else -> { false }
//            }
//        return b    // handle of menu goes upwards if false
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)

//        val startFOB = findViewById<Button>(R.id.floatingActionButtonStart)
//        val optionsButton = findViewById<Button>(R.id.optionsButton)
//        val optionsAction = findViewById<ActionMenuItem>(R.id.action_options)
//        val signInButton = findViewById<Button>(R.id.signInButton)
//        val registerButton = findViewById<Button>(R.id.registerButton)
//        val logOutAction = findViewById<ActionMenuItem>(R.id.action_logout)


        // widget animation from xml file
//        val shake = AnimationUtils.loadAnimation(this, R.anim.shake)

        // Set onClick(v: View?) function for button view generated click events.
        // This works because OnClickListener interface only has one method.
        // _ represents unused parameter by lambda function
        // launch a standard game on new activity
//        startFOB.setOnClickListener { _ ->   // may start lambda with (var1, ..., varn) -> or leave out if no parameter
//            Snackbar.make(startFOB/*any widget to find root ViewGroup*/, "START!!!", Snackbar.LENGTH_SHORT).show()
//            startFOB.startAnimation(shake)
//        }
        // Options - Action menu choice
        //
//        optionsAction.setOnClickListener { _ ->
//            Snackbar.make(optionsAction, "No Options available yet", Snackbar.LENGTH_SHORT).show()
//        }
        //
//        signInButton.setOnClickListener { _ ->
//            Snackbar.make(signInButton, "Local Data Only Now", Snackbar.LENGTH_SHORT).show()
//        }
        //
//        registerButton.setOnClickListener { _ ->
//            Snackbar.make(registerButton, "Register not available yet", Snackbar.LENGTH_SHORT).show()
//        }
        // Log out - Action menu choice TODO
//        logOutAction.setOnClickListener { _ ->
//            Snackbar.make(logOutAction, "Register not available yet, so no log out either", Snackbar.LENGTH_SHORT).show()
//        }
    }
}