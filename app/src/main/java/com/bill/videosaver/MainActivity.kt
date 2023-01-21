package com.bill.videosaver

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import android.os.Bundle
import com.bill.videosaver.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navigator = AppNavigator(this, R.id.container)

    private val navigatorHolder: NavigatorHolder by inject()
    private val router: Router by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        router.navigateTo(Screens.homeScreen())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}

