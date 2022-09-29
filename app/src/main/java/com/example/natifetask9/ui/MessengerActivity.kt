package com.example.natifetask9.ui

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.natifetask9.R
import com.example.natifetask9.databinding.ActivityMessengerBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessengerActivity : AppCompatActivity() {

    private var binding: ActivityMessengerBinding? = null
    private val viewModel by viewModel<MessengerActivityViewModel>()
    private var contentHasLoaded = false
    private var userHasLogged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMessengerBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        observeViewModel()
        setupSplashScreen()
    }

    private fun setupSplashScreen() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (contentHasLoaded) {
                        setStartDestination()
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            }
        )
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.isUserLoggedIn.collect {
                    userHasLogged = it
                    contentHasLoaded = true
                }
            }
        }
    }

    private fun setStartDestination() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        if (userHasLogged) {
            navGraph.setStartDestination(R.id.user_list_fragment)
        } else {
            navGraph.setStartDestination(R.id.auth_fragment)
        }
        navController.graph = navGraph
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}