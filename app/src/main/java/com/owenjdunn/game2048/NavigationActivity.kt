package com.owenjdunn.game2048

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

/**
 * Activity to host the navigation controller that uses a navigation graph described through its
 * XML file, the navigation layout, an action bar.
 */
class NavigationActivity : AppCompatActivity() {
    private lateinit var appBarConfig: AppBarConfiguration
//    private val viewModel by activityViewModels<UserDataViewModel>()

    /**
     * Set up the app bar from the navigation controller graph to be used with the nav controller.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val nc = findNavController(R.id.nav_host_fragment)
        appBarConfig = AppBarConfiguration(nc.graph)
        setupActionBarWithNavController(nc, appBarConfig)
    }

    /**
     * Make the "up" button behave identically to the "back" button.
     */
    override fun onSupportNavigateUp(): Boolean {
        val nc = findNavController(R.id.nav_host_fragment)
        return nc.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    /**
     *  Allow back button of toolbar to go back to login fragment.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}